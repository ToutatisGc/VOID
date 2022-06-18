package cn.toutatis.spring.core.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public static final String UNCHECK_CODE = "980617";

    public static final String PERMISSION_DENIED_CODE = "971111";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

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
//        Object principal = authentication.getPrincipal();
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
//    /**
//     * @param response  response
//     * @param o 序列化实体类
//     * @throws IOException 流异常
//     */
//    private void returnJson(HttpServletRequest request, HttpServletResponse response,Object o) throws IOException {
//        corsResponse(request,response);
//        encodeReturn(response);
//        response.getWriter().write(JSON.toJSONString(o));
//    }
//
//    /**
//     * @param response response
//     * 编码为application/json
//     */
//    private void encodeReturn(HttpServletResponse response){
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
////        response.setStatus(HttpStatus.BAD_REQUEST.value());
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
