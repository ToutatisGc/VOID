package cn.toutatis.xvoid.spring.core.security.config.handler;

import cn.toutatis.xvoid.common.standard.StandardFields;
import cn.toutatis.xvoid.data.common.result.ProxyResult;
import cn.toutatis.xvoid.data.common.result.ResultCode;
import cn.toutatis.xvoid.data.common.security.SystemAuthRole;
import cn.toutatis.xvoid.spring.VoidModuleInfo;
import cn.toutatis.xvoid.spring.business.user.entity.AuthInfo;
import cn.toutatis.xvoid.spring.business.user.service.SystemAuthPathService;
import cn.toutatis.xvoid.spring.business.user.service.SystemAuthRoleService;
import cn.toutatis.xvoid.spring.core.security.access.ValidationMessage;
import cn.toutatis.xvoid.spring.core.security.access.VoidSecurityAuthenticationService;
import cn.toutatis.xvoid.support.spring.config.VoidConfiguration;
import cn.toutatis.xvoid.support.spring.core.aop.advice.ResponseResultDispatcherAdvice;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static cn.toutatis.xvoid.common.standard.StandardFields.VOID_HTTP_ATTRIBUTE_STATUS_KEY;

/**
 * @author Toutatis_Gc
 *
 */
@Component
public class SecurityHandler implements AuthenticationSuccessHandler,
                                        AuthenticationFailureHandler,
          /*各种回调类*/                 AuthenticationEntryPoint,
                                        AccessDeniedHandler {

    /**
     * 是否跳转页面
     * 从HttpServletRequest 获取的参数
     */
    private static final String REQUEST_PARAMETER_WHETHER_REDIRECT_PAGE = "REDIRECT_PAGE";

    /**
     * 自定义跳转页面
     */
    private static final String REQUEST_CUSTOM_REDIRECT_PAGE_MAPPING = "CUSTOM_REDIRECT_PAGE_MAPPING";

    private final RequestMethodResolver requestMethodResolver;

    private final Logger logger = LoggerToolkit.getLogger(this.getClass());

    private final ResponseResultDispatcherAdvice responseResultDispatcherAdvice;

    private final VoidConfiguration voidConfiguration;

    private final VoidConfiguration.GlobalServiceConfig globalServiceConfig;

    private final SystemAuthRoleService systemAuthRoleService;

    private final SystemAuthPathService systemAuthPathService;



    public SecurityHandler(RequestMethodResolver requestMethodResolver, ResponseResultDispatcherAdvice responseResultDispatcherAdvice, VoidConfiguration voidConfiguration, SystemAuthRoleService systemAuthRoleService, SystemAuthPathService systemAuthPathService) {
        this.requestMethodResolver = requestMethodResolver;
        this.responseResultDispatcherAdvice = responseResultDispatcherAdvice;
        this.voidConfiguration = voidConfiguration;
        this.globalServiceConfig = voidConfiguration.getGlobalServiceConfig();
        this.systemAuthRoleService = systemAuthRoleService;
        this.systemAuthPathService = systemAuthPathService;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        if (principal instanceof AuthInfo){
            JSONObject userInfo = ((AuthInfo) principal).getUserInfo();
            List<SystemAuthRole> roles = systemAuthRoleService.getUserRoles(userInfo.getString("id"));
            List<String> userPermissionsStrings = systemAuthPathService.getUserPermissionsStrings(roles);
            ((AuthInfo) principal).setPermissions(userPermissionsStrings);
        }else{
            logger.error(authentication.toString());
        }
        this.returnJson(response,responseResultDispatcherAdvice.proxyResult(new ProxyResult(ResultCode.AUTHENTICATION_SUCCESSFUL)));
    }

    /**
     * 无权限访问控制
     * @param request 请求
     * @param response 响应
     * @param authException 权限异常
     * @throws IOException 重定向异常
     * @throws ServletException 重定向异常
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException){
        // org.springframework.security.authentication.InsufficientAuthenticationException: Full authentication is required to access this resource
        if (authException instanceof InsufficientAuthenticationException){
            // 特定异常
        }else {
            logger.error("[{}]未分配异常处理器", VoidModuleInfo.MODULE_NAME);
        }
        ResultCode anonymityStatus = ResultCode.ANONYMITY_FAILED;
        requestMethodResolver.resolveMethod(request, response).handle(
                ()->{
                    request.setAttribute(VOID_HTTP_ATTRIBUTE_STATUS_KEY, anonymityStatus.name());
                    this.forwardPage(request,response,"/error");
                },
                () ->{
                    ProxyResult proxyResult = new ProxyResult(anonymityStatus);
                    proxyResult.setUseDetailedMode(globalServiceConfig.getUseDetailedMode());
                    this.returnJson(response,responseResultDispatcherAdvice.proxyResult(proxyResult));
                },
                ()->{
                    logger.warn("[{}]未处理METHOD:{},异常为:{}", VoidModuleInfo.MODULE_NAME,request.getMethod(),authException);
                    authException.printStackTrace();
                }
        );

    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        System.err.println(666);
    }

    /**
     * 登陆失败
     * @param request 请求
     * @param response 响应
     * @param exception 异常
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ProxyResult proxyResult;
        String message;
        if (exception instanceof UsernameNotFoundException){
            Object attributeCode = request.getAttribute(VOID_HTTP_ATTRIBUTE_STATUS_KEY);
            if (attributeCode instanceof ResultCode){
                proxyResult =  new ProxyResult((ResultCode) attributeCode);
            }else{
                proxyResult = new ProxyResult(ResultCode.ILLEGAL_OPERATION);
            }
            String type = exception.getMessage();
            Object requestInfo = request.getAttribute(StandardFields.VOID_HTTP_ATTRIBUTE_MESSAGE_KEY);
            if (VoidSecurityAuthenticationService.MessageType.STRING == VoidSecurityAuthenticationService.MessageType.valueOf(type)){
                proxyResult.setMessage((String) requestInfo);
            }else {
                JSONObject requestInfoJson = (JSONObject) requestInfo;
                proxyResult.setMessage(requestInfoJson.getString("message"));
            }
        }else if (exception instanceof BadCredentialsException){
            proxyResult = new ProxyResult(ResultCode.AUTHENTICATION_FAILED);
        }else if (exception instanceof AccountStatusException){
            proxyResult = new ProxyResult(ResultCode.CHECKED_FAILED);
            if (exception.getClass() == LockedException.class) {
                proxyResult.setMessage(ValidationMessage.ACCOUNT_LOCKED);
            }else if (exception.getClass() == AccountExpiredException.class) {
                proxyResult.setMessage(ValidationMessage.CONNECT_EXPIRED);
            }else if (exception.getClass() == DisabledException.class) {
                proxyResult.setMessage(ValidationMessage.ACCOUNT_DISABLED);
            } else {
                logger.error("[{}]认证未记录异常：{}", VoidModuleInfo.MODULE_NAME,exception.toString());
            }
        }else if (exception instanceof AuthenticationServiceException){
            proxyResult = new ProxyResult(ResultCode.AUTHENTICATION_FAILED);
        }else {
            logger.error("[{}]认证未记录异常：{}", VoidModuleInfo.MODULE_NAME,exception.toString());
            proxyResult = new ProxyResult(ResultCode.INNER_EXCEPTION);
        }
        this.returnJson(response,responseResultDispatcherAdvice.proxyResult(proxyResult));
    }

    private void jumpToPage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String whether = request.getParameter(REQUEST_PARAMETER_WHETHER_REDIRECT_PAGE);
        if (Validator.strNotBlank(whether) && ("true".equalsIgnoreCase(whether) || "false".equalsIgnoreCase(whether))){
            boolean whetherBool = Boolean.parseBoolean(whether);
            if (whetherBool){
                String pageMapping = request.getParameter(REQUEST_CUSTOM_REDIRECT_PAGE_MAPPING);
                if (Validator.strIsBlank(pageMapping)){
                    request.getRequestDispatcher("/error").forward(request,response);
                }else {
                    request.getRequestDispatcher("/error").forward(request,response);
                }
            }
        }
    }

    /**
     * @param response  response
     * @param o 序列化实体类
     * @throws IOException 流异常
     */
    private void returnJson(HttpServletResponse response,Object o) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(JSON.toJSONString(o));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void forwardPage(HttpServletRequest request,HttpServletResponse response,String url) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        try {
            request.getRequestDispatcher(url).forward(request,response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }




//    @Autowired
//    private SystemUserPermissionMapper systemUserPermissionMapper;
//
//    @Autowired
//    private WechatUserDetailsService wechatUserDetailsService;
//
//    /**
//     * @see AuthenticationSuccessHandler
//     * @throws IOException
//     * @throws ServletException
//     * 认证成功情况
//     */
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        Object principal = authentication.getPrincipal();4
//        if (principal instanceof AccountEntity){
//            AccountEntity accountEntity = (AccountEntity) principal;
//            switch (accountEntity.getAccountPermissionEnum()){
//                case BUSINESS:
//                    ArrayList<String> redirectPermissions = new ArrayList<>(1);
//                    redirectPermissions.add(request.getContextPath()+"/getManagerInfo");
//                    accountEntity.setPermissions(redirectPermissions);
//                    break;
//                case DEV:
//
//                case REMOTE_DOUBLE_CHECK:
//                default:
//                    break;
//            }
//        }else if (principal instanceof JWTEntity){
//            JWTEntity jwtEntity = (JWTEntity) principal;
//            String token = jwtEntity.getToken();
//            response.setHeader("Void-Token",token);
////            request.getRequestDispatcher("http://wuyang.natapp1.cc/crystal/authentication").forward(request,response);
//            response.sendRedirect("http://wuyang.natapp1.cc/crystal/authentication");
//        }else if (principal instanceof SecretEntity){
//            SecretEntity secretEntity = (SecretEntity) principal;
//            List<String> permissionsRelation = systemUserPermissionMapper.getPermissionsRelation(secretEntity.getUuid());
//            secretEntity.setPermissions(permissionsRelation);
//            WechatUserDetails byId = wechatUserDetailsService.getById(secretEntity.getUuid());
//            secretEntity.setHeadPortrait(byId.getHeadPortrait());
//            String redirectPage = secretEntity.getRedirectPage();
//            if (redirectPage != null){
//                corsResponse(request,response);
//                response.sendRedirect(redirectPage);
//            }
//        }
//        Result result = new Result(ResultCode.AUTHENTICATION_SUCCESSFUL);
//        returnJson(request,response,result);
//    }
//
//
//
//    /**
//     * @see AuthenticationFailureHandler
//     * @throws IOException
//     * @throws ServletException
//     * 认证失败情况
//     */
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        Object encodeReturn = encodeReturn(request, response);
//        System.err.println(exception);
//        Result result = new Result(false);
//        String message;
//        if (exception instanceof UsernameNotFoundException){
//            message = exception.getMessage();
//        }else if (exception instanceof BadCredentialsException){
//            message = ResultCode.AUTHENTICATION_FAILED.getExtraInfo();
//        }else {
//            message = "内部错误";
//        }
//        result.setMessage(message);
//        result.setCode(ResultCode.AUTHENTICATION_FAILED);
//        returnJson(request,response,result);
//    }
//
//    /**
//     * @see AuthenticationEntryPoint
//     * @throws IOException
//     * @throws ServletException
//     * 匿名未认证处理
//     */
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        redirect(request,response,UNCHECK_CODE);
//        Result result = new Result(false);
//        result.setCode(ResultCode.ANONYMITY_FAILED);
//        result.setMessage(ResultCode.ANONYMITY_FAILED);
//        returnJson(request,response,result);
//    }
//
//    /**
//     * @throws IOException
//     * @throws ServletException
//     * @see AccessDeniedHandler
//     * 认证成功访问，但没有权限
//     */
//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        redirect(request,response,PERMISSION_DENIED_CODE);
//        Result result = new Result(false);
//        result.setCode(ResultCode.PERMISSION_DEFINED_FAILED);
//        result.setMessage(ResultCode.PERMISSION_DEFINED_FAILED);
//        returnJson(request,response,result);
//    }
//
//    private Object encodeReturn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        Object attribute = session.getAttribute(ValidationMessage.VALIDATION_SESSION_KEY);
//        if (attribute != null){
//            session.removeAttribute(ValidationMessage.VALIDATION_SESSION_KEY);
//            if (attribute instanceof String){
//                String value = String.valueOf(attribute);
//                String forward = "forward:";
//                if (value.startsWith(forward)){
//                    String url = value.replace(forward, "");
//                    request.getRequestDispatcher(url).forward(request,response);
//                }
//            }
//        }
//        encodeReturn(response);
//        return attribute;
//    }
//
//    public void redirect(HttpServletRequest request,HttpServletResponse response,String code) throws IOException, ServletException {
//        String method = request.getMethod();
//        if ("GET".equals(method)){
//            String requestHeaderWith = request.getHeader("X-Requested-With");
//            String userAgent = request.getHeader("User-Agent");
//            if (userAgent != null || "XMLHttpRequest".equals(requestHeaderWith)){
//                request.getRequestDispatcher("/authErrorPage?code="+code).forward(request,response);
//            }
//        }
//    }
//
//    private void corsResponse(HttpServletRequest request,HttpServletResponse response){
//        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Authorization, X-Requested-With, Cookie");
//    }
//
//    private void jumpToPage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
//        String whether = request.getParameter(REQUEST_PARAMETER_WHETHER_REDIRECT_PAGE);
//        ObjectToolkit objectToolkit = ObjectToolkit.getInstance();
//        if (objectToolkit.strNotBlank(whether) && ("true".equalsIgnoreCase(whether) || "false".equalsIgnoreCase(whether))){
//            boolean whetherBool = Boolean.parseBoolean(whether);
//            if (whetherBool){
//                String pageMapping = request.getParameter(REQUEST_CUSTOM_REDIRECT_PAGE_MAPPING);
//                if (objectToolkit.strIsBlank(pageMapping)){
//                    request.getRequestDispatcher("/error").forward(request,response);
//                }else {
//                    request.getRequestDispatcher("/error").forward(request,response);
//                }
//            }
//        }
//    }

}
