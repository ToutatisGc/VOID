package cn.toutatis.xvoid.spring.core.security.access.auth

import cn.toutatis.redis.RedisCommonKeys
import cn.toutatis.xvoid.spring.business.user.service.FormUserAuthService
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration
import cn.toutatis.xvoid.spring.configure.system.VoidSecurityConfiguration
import cn.toutatis.xvoid.spring.configure.system.enums.global.RunMode
import cn.toutatis.xvoid.spring.core.security.access.ValidationMessage
import cn.toutatis.xvoid.toolkit.validator.Validator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

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

    @Autowired
    private lateinit var httpSession: HttpSession

    companion object{
        /**
         * Auth Times Key 账户登录重试次数
         */
        const val LOGIN_RETRY_TIMES_KEY = "VOID_RETRY_AUTH_TIMES"

        const val LOGIN_PRE_CHECK_KEY = RedisCommonKeys.SESSION_KEY+".pre-check"
    }

    fun findSimpleUser(account: String): UserDetails {
        this.preCheck(account)
        return formUserAuthService.findSimpleUser(account)
    }


    override fun preCheck(account: String): Boolean {
        if (Validator.strIsBlank(account)) throwFailed(ValidationMessage.USERNAME_BLANK)
        if (!Validator.checkCNUsername(account)) throwFailed(ValidationMessage.USERNAME_ILLEGAL)
        if (voidGlobalConfiguration.mode == RunMode.DEBUG){ return true }
        val loginConfig = voidSecurityConfiguration.loginConfig
        if (loginConfig.beforeLoginCheckUsername){
            System.err.println(httpServletRequest.session.id)
            val sessionOps = redisTemplate.boundValueOps(RedisCommonKeys.concat(LOGIN_PRE_CHECK_KEY,httpServletRequest.session.id))
            val store = sessionOps.get()
            if (store == null || store != account) {
                throwFailed(ValidationMessage.USERNAME_NOT_PRE_CHECK)
            }
        }
        if (loginConfig.loginRetryLimitEnabled){
            val loginRetryOps = redisTemplate.boundHashOps<String, String>(account)
            val currentRetry = loginRetryOps.get(LOGIN_RETRY_TIMES_KEY)
            if (currentRetry != null){
                val loginRetryTimes = loginConfig.loginRetryTimes
            }
        }
        return true
    }

}