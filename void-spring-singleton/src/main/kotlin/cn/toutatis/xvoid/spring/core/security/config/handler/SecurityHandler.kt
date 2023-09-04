package cn.toutatis.xvoid.spring.core.security.config.handler

import cn.toutatis.redis.RedisCommonKeys.concat
import cn.toutatis.xvoid.common.result.ProxyResult
import cn.toutatis.xvoid.common.result.Result
import cn.toutatis.xvoid.common.result.ResultCode
import cn.toutatis.xvoid.common.standard.AuthFields
import cn.toutatis.xvoid.common.standard.StandardFields
import cn.toutatis.xvoid.spring.VoidModuleInfo
import cn.toutatis.xvoid.spring.business.user.entity.AuthInfo
import cn.toutatis.xvoid.spring.business.user.service.SystemAuthPathService
import cn.toutatis.xvoid.spring.business.user.service.SystemAuthRoleService
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration.GlobalServiceConfig
import cn.toutatis.xvoid.spring.configure.system.VoidSecurityConfiguration
import cn.toutatis.xvoid.spring.configure.system.enums.global.RunMode
import cn.toutatis.xvoid.spring.core.security.access.ValidationMessage
import cn.toutatis.xvoid.spring.core.security.access.VoidSecurityAuthenticationService
import cn.toutatis.xvoid.spring.support.Meta
import cn.toutatis.xvoid.spring.support.core.aop.advice.ResponseResultDispatcherAdvice
import cn.toutatis.xvoid.spring.support.toolkits.VoidSpringToolkit
import cn.toutatis.xvoid.toolkit.constant.Time
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit.getLogger
import cn.toutatis.xvoid.toolkit.log.errorWithModule
import cn.toutatis.xvoid.toolkit.validator.Validator.strIsBlank
import cn.toutatis.xvoid.toolkit.validator.Validator.strNotBlank
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import java.time.ZoneId
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.math.abs


/**
 * @author Toutatis_Gc
 */
@Component
class SecurityHandler(
    private val requestMethodResolver: RequestMethodResolver,
    private val responseResultDispatcherAdvice: ResponseResultDispatcherAdvice,
    private val voidGlobalConfiguration: VoidGlobalConfiguration,
    private val systemAuthRoleService: SystemAuthRoleService,
    private val systemAuthPathService: SystemAuthPathService,
    private val voidSecurityConfiguration: VoidSecurityConfiguration,
    private val voidSpringToolkit: VoidSpringToolkit,
    private val redisTemplate: RedisTemplate<String, Any>,
) : AuthenticationSuccessHandler, AuthenticationFailureHandler, AuthenticationEntryPoint, AccessDeniedHandler {

    companion object {
        /**
         * 是否跳转页面
         * 从HttpServletRequest 获取的参数
         */
        private const val REQUEST_PARAMETER_WHETHER_REDIRECT_PAGE = "REDIRECT_PAGE"

        /**
         * 自定义跳转页面
         */
        private const val REQUEST_CUSTOM_REDIRECT_PAGE_MAPPING = "CUSTOM_REDIRECT_PAGE_MAPPING"
    }

    private val logger = getLogger(this.javaClass)

    private val globalServiceConfig: GlobalServiceConfig = voidGlobalConfiguration.globalServiceConfig

    private val loginConfig: VoidSecurityConfiguration.LoginConfig = voidSecurityConfiguration.loginConfig

    /**
     * On authentication success 登陆成功情况
     * 登陆成功后处理的步骤如下:
     * 1. 赋予用户角色
     * 2. 赋予对应的路由权限
     * 3. 赋予一些特殊权限(如管理员权限,运维权限等)
     * @param request 请求对象(此时的request为servlet正常请求)
     * @param response 响应对象
     * @param authentication 权限对象
     */
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication ) {
        val principal = authentication.principal
        val loginSuccessfulResult:ProxyResult
        val runMode = voidGlobalConfiguration.mode
        if (principal is AuthInfo) {
            loginSuccessfulResult = ProxyResult(ResultCode.AUTHENTICATION_SUCCESSFUL)
            val userInfo = principal.userInfo
            val roles = systemAuthRoleService.getUserRoles(userInfo.getString("id"))
            val userPermissionsStrings = systemAuthPathService.getUserPermissionsStrings(roles)
            principal.permissions = userPermissionsStrings
        } else {
            loginSuccessfulResult = ProxyResult(ResultCode.INNER_EXCEPTION)
            if (runMode == RunMode.DEV ||runMode == RunMode.DEBUG){
                loginSuccessfulResult.supportMessage = "意外登录情况:${authentication.toString()}"
            }else{
                loginSuccessfulResult.supportMessage = "意外登录情况"
            }
            logger.errorWithModule(Meta.MODULE_NAME,"SECURITY",loginSuccessfulResult.supportMessage)
        }
        returnJson(response,
            responseResultDispatcherAdvice.proxyResult(loginSuccessfulResult)
        )
    }

    /**
     * 无权限访问控制
     * @param request 请求
     * @param response 响应
     * @param authException 权限异常
     * @throws IOException 重定向异常
     * @throws ServletException 重定向异常
     */
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        // org.springframework.security.authentication.InsufficientAuthenticationException: Full authentication is required to access this resource
        if (authException is InsufficientAuthenticationException) {
            // 特定异常
        } else {
            logger.error("[{}]未分配异常处理器", VoidModuleInfo.MODULE_NAME)
        }
        val anonymityStatus = ResultCode.ANONYMITY_FAILED
        requestMethodResolver.resolveMethod(request, response).handle(
            {
                request.setAttribute(StandardFields.VOID_HTTP_ATTRIBUTE_STATUS_KEY, anonymityStatus.name)
                forwardPage(request, response, "/error")
            },
            {
                val proxyResult = ProxyResult(anonymityStatus)
                proxyResult.useDetailedMode = globalServiceConfig.useDetailedMode
                returnJson(response, responseResultDispatcherAdvice.proxyResult(proxyResult))
            }
        ) {
            logger.warn("[{}]未处理METHOD:{},异常为:{}", VoidModuleInfo.MODULE_NAME, request.method, authException)
            authException.printStackTrace()
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException,
    ) {
        System.err.println(666)
    }

    /**
     * 登陆失败
     * @param request 请求
     * @param response 响应
     * @param exception 异常
     * @throws IOException
     * @throws ServletException
     * @see cn.toutatis.xvoid.spring.core.security.access.auth.LocalUserService
     */
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException,
    ) {
        val proxyResult: Result
        var message: String
        if (exception is UsernameNotFoundException) {
            val attributeCode = request.getAttribute(StandardFields.VOID_HTTP_ATTRIBUTE_STATUS_KEY)
            proxyResult = if (attributeCode is ResultCode) {
                ProxyResult(attributeCode)
            } else {
                ProxyResult(ResultCode.ILLEGAL_OPERATION)
            }
            val type = exception.message
            val requestInfo = request.getAttribute(StandardFields.VOID_HTTP_ATTRIBUTE_MESSAGE_KEY)
            if (VoidSecurityAuthenticationService.MessageType.STRING == VoidSecurityAuthenticationService.MessageType.valueOf(type!!)) {
                proxyResult.message = requestInfo as String
            } else {
                val requestInfoJson = requestInfo as JSONObject
                proxyResult.message = requestInfoJson.getString("message")
            }
            /*用户名密码错误*/
        } else if (exception is BadCredentialsException) {
            /*登录失败增加重试次数锁定账户*/
            proxyResult = if (loginConfig.loginRetryLimitEnabled) {
                tryLockAccount(request)
            } else {
                ProxyResult(ResultCode.AUTHENTICATION_FAILED)
            }
            /*账户状态错误*/
        } else if (exception is AccountStatusException) {
            proxyResult = ProxyResult(ResultCode.CHECKED_FAILED)
            if (exception.javaClass == LockedException::class.java) {
                proxyResult.message = ValidationMessage.ACCOUNT_LOCKED
            } else if (exception.javaClass == AccountExpiredException::class.java) {
                proxyResult.message = ValidationMessage.CONNECT_EXPIRED
            } else if (exception.javaClass == DisabledException::class.java) {
                proxyResult.message = ValidationMessage.ACCOUNT_DISABLED
            } else {
                logger.error("[{}]认证未记录异常：{}", VoidModuleInfo.MODULE_NAME, exception.toString())
            }
        } else if (exception is AuthenticationServiceException) {
            proxyResult = ProxyResult(ResultCode.AUTHENTICATION_FAILED)
            val authStart = cn.toutatis.xvoid.common.exception.AuthenticationException.AUTH_START_MESSAGE
            if (exception.message!!.startsWith(authStart)) {
                val errorMessage = exception.message!!.replace(authStart, "")
                proxyResult.supportMessage = errorMessage
            }
        } else {
            logger.error("[{}]认证未记录异常：{}", VoidModuleInfo.MODULE_NAME, exception.toString())
            proxyResult = ProxyResult(ResultCode.INNER_EXCEPTION)
        }
        returnJson(response, responseResultDispatcherAdvice.proxyResult(proxyResult))
    }

    @Throws(ServletException::class, IOException::class)
    private fun jumpToPage(request: HttpServletRequest, response: HttpServletResponse) {
        val whether = request.getParameter(REQUEST_PARAMETER_WHETHER_REDIRECT_PAGE)
        if (strNotBlank(whether) && ("true".equals(whether, ignoreCase = true) || "false".equals(whether,
                ignoreCase = true))
        ) {
            if (whether.toBoolean()) {
                val pageMapping = request.getParameter(REQUEST_CUSTOM_REDIRECT_PAGE_MAPPING)
                if (strIsBlank(pageMapping)) {
                    request.getRequestDispatcher("/error").forward(request, response)
                } else {
                    request.getRequestDispatcher("/error").forward(request, response)
                }
            }
        }
    }

    /**
     * @param response  response
     * @param o 序列化实体类
     * @throws IOException 流异常
     */
    private fun returnJson(response: HttpServletResponse, o: Any?) {
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        try {
            response.writer.write(JSON.toJSONString(o))
        } catch (e: Exception) {
            logger.errorWithModule(Meta.MODULE_NAME,"AUTH","SecurityHandler异常[${e::class}:${e.message}]")
            val error = ProxyResult(ResultCode.INNER_EXCEPTION)
            returnJson(response,responseResultDispatcherAdvice.proxyResult(error))
        }
    }

    private fun forwardPage(request: HttpServletRequest, response: HttpServletResponse, url: String) {
        response.status = HttpStatus.FORBIDDEN.value()
        try {
            request.getRequestDispatcher(url).forward(request, response)
        } catch (e: ServletException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Try lock account 锁定账户
     * 如果开启登录限制
     * 则登录失败时会根据限制策略锁定账户
     * 登录成功将移除相关redis缓存
     * If the login restriction is enabled
     * The account will be locked based on the restriction policy if the login fails
     * Successful login removes the relevant redis cache
     * @param request 请求对象
     * @return 锁定账户结果
     */
    private fun tryLockAccount(request: HttpServletRequest):Result{
        val jSessionId = voidSpringToolkit.getJSessionId(request)
        val loginAccountOps = redisTemplate.boundValueOps(concat(AuthFields.LOGIN_ACCOUNT_SESSION_KEY, jSessionId))
        val loginAccount = loginAccountOps.get()
        if (strNotBlank(loginAccount)) {
            /*没有缓存添加一条*/
            val retryOps = redisTemplate.boundValueOps(concat(AuthFields.LOGIN_RETRY_TIMES_KEY, loginAccount))
            var retryTimes = retryOps.get() as Int?
            if (retryTimes == null) retryTimes = 1 else retryTimes++
            retryOps.set(retryTimes)
            /*截止到今日结束*/
            if (retryOps.expire == null || retryOps.expire == -1L){
                retryOps.expireAt(Time.getCurrentDayLastMillsTime().atZone(ZoneId.systemDefault()).toInstant())
            }
            val loginRetryTimes = loginConfig.loginRetryTimes
            if (retryTimes > loginRetryTimes){
                val loginFailedLockTime = loginConfig.loginFailedLockTime
                val currentTimeByLong:String
                val lockDuration:Long
                /*计算锁定时长*/
                if (loginConfig.loginFailedLockTimeProgressiveIncrease){
                    val exceedance = abs(retryTimes - loginRetryTimes)
                    lockDuration = Time.currentMillis + (exceedance * loginFailedLockTime.toMillis())
                    currentTimeByLong = Time.getCurrentTimeByLong(lockDuration)
                }else{
                    lockDuration = Time.currentMillis+loginFailedLockTime.toMillis()
                    currentTimeByLong = Time.getCurrentTimeByLong(lockDuration)
                }
                val lockedOps = redisTemplate.boundValueOps(concat(AuthFields.LOGIN_ACCOUNT_LOCKED_KEY, loginAccount))
                /*如果锁定大于今日,提醒今日不可登录*/
                if (lockDuration > Time.parseTimeToMills(Time.getCurrentDayLastMillsTime())){
                    lockedOps.set(ValidationMessage.ACCOUNT_LOCKED_TODAY)
                }else{
                    lockedOps.set(Time.regexTime(Time.HMS_COLON_FORMAT_REGEX, lockDuration))
                }
                lockedOps.expireAt(Time.parseData(currentTimeByLong))
                val proxyResult = ProxyResult(ResultCode.AUTHENTICATION_FAILED)
                proxyResult.supportMessage = ValidationMessage.ACCOUNT_LOCKED
                return proxyResult
            }else{
                val proxyResult = ProxyResult(ResultCode.AUTHENTICATION_FAILED)
                proxyResult.supportMessage = ValidationMessage.ACCOUNT_WILL_LOCK.format(loginRetryTimes-retryTimes)
                return proxyResult
            }
        } else {
            return ProxyResult(ResultCode.AUTHENTICATION_PRE_CHECK_FAILED)
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