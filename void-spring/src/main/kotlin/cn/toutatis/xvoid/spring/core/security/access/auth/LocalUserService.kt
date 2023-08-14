package cn.toutatis.xvoid.spring.core.security.access.auth

import cn.toutatis.xvoid.common.exception.AuthenticationException
import cn.toutatis.xvoid.spring.business.user.service.FormUserAuthService
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration
import cn.toutatis.xvoid.spring.configure.system.VoidSecurityConfiguration
import cn.toutatis.xvoid.spring.configure.system.enums.global.RunMode
import cn.toutatis.xvoid.spring.core.security.access.ValidationMessage
import cn.toutatis.xvoid.toolkit.validator.Validator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest
import kotlin.jvm.Throws

@Service
class LocalUserService : VoidAuthService {

    @Autowired
    private lateinit var formUserAuthService : FormUserAuthService

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, Any>

    @Autowired
    private lateinit var voidSecurityConfiguration: VoidSecurityConfiguration

    @Autowired
    private lateinit var voidGlobalConfiguration: VoidGlobalConfiguration

    @Autowired
    private lateinit var httpServletRequest: HttpServletRequest

    companion object{
        /**
         * Auth Times Key 账户登录重试次数
         */
        const val LOGIN_RETRY_TIMES_KEY = "VOID_RETRY_AUTH_TIMES"

        const val LOGIN_CHECK_USERNAME_KEY = "VOID_LOGIN_CHECK_USERNAME"
    }

    fun findSimpleUser(username: String): UserDetails {
        this.preCheck(username)
        return formUserAuthService.findSimpleUser(username)
    }

    override fun preCheck(username: String): Boolean {
        if (Validator.strIsBlank(username)) throwFailed(ValidationMessage.USERNAME_BLANK)
        if (!Validator.checkCNUsername(username)) throwFailed(ValidationMessage.USERNAME_ILLEGAL)
        if (voidGlobalConfiguration.mode == RunMode.DEBUG){ return true }
        val loginConfig = voidSecurityConfiguration.loginConfig
        if (loginConfig.beforeLoginCheckUsername){
            val loginCheckOps = redisTemplate.boundHashOps<String, Boolean>(username)
            val checkUsername = if (loginCheckOps.get(LOGIN_CHECK_USERNAME_KEY) != null) loginCheckOps.get(LOGIN_CHECK_USERNAME_KEY) else false
            if (!checkUsername!!) throwFailed(ValidationMessage.USERNAME_NOT_PRE_CHECK)
        }
        if (loginConfig.loginRetryLimitEnabled){
            val loginRetryOps = redisTemplate.boundHashOps<String, String>(username)
            val currentRetry = loginRetryOps.get(LOGIN_RETRY_TIMES_KEY)
            if (currentRetry != null){
                val loginRetryTimes = loginConfig.loginRetryTimes
            }
        }
        return true
    }

}