package cn.toutatis.xvoid.spring.core.security.access.auth

import cn.toutatis.redis.RedisCommonKeys
import cn.toutatis.xvoid.common.standard.StandardFields
import cn.toutatis.xvoid.spring.business.user.service.FormUserAuthService
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration
import cn.toutatis.xvoid.spring.configure.system.VoidSecurityConfiguration
import cn.toutatis.xvoid.spring.configure.system.enums.global.RunMode
import cn.toutatis.xvoid.spring.core.security.access.AuthType
import cn.toutatis.xvoid.spring.core.security.access.ValidationMessage
import cn.toutatis.xvoid.toolkit.validator.Validator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

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

        const val AUTH_KEY_PREFIX = "${StandardFields.SYSTEM_PREFIX}:AUTH:"

        const val LOGIN_PRE_CHECK_KEY = "${AUTH_KEY_PREFIX}PRE-CHECK:%s"

        /**
         * Auth Times Key 账户登录重试次数
         */
        const val LOGIN_RETRY_TIMES_KEY = "${AUTH_KEY_PREFIX}:RETRY-TIMES:%s"
    }

    fun findSimpleUser(account: String): UserDetails {
        this.preCheckAccount(account,AuthType.ACCOUNT_NORMAL)
        return formUserAuthService.findSimpleUser(account)
    }


    override fun preCheckAccount(account: String, authType: AuthType): Boolean {
        if (Validator.strIsBlank(account)) throwFailed(ValidationMessage.USERNAME_BLANK)
        if (!Validator.checkCNUsername(account)) throwFailed(ValidationMessage.USERNAME_ILLEGAL)
        if (voidGlobalConfiguration.mode == RunMode.DEBUG){ return true }
        val loginConfig = voidSecurityConfiguration.loginConfig
        if (loginConfig.beforeLoginCheckUsername){
            val sessionOps = redisTemplate.boundValueOps(RedisCommonKeys.concat(LOGIN_PRE_CHECK_KEY,account))
            val store = sessionOps.get()
            if (store == null || store != true) {
                throwFailed(ValidationMessage.USERNAME_NOT_PRE_CHECK)
            }
        }
        if (loginConfig.loginRetryLimitEnabled){
            val loginRetryOps = redisTemplate.boundValueOps(RedisCommonKeys.concat(LOGIN_RETRY_TIMES_KEY,account))
            val currentRetryTimes = loginRetryOps.get() as Int?
            if (currentRetryTimes!= null){
                val loginRetryTimes = loginConfig.loginRetryTimes
                if (currentRetryTimes > loginRetryTimes){
                    // TODO 添加解锁时间
                    throwFailed(ValidationMessage.ACCOUNT_LOCKED)
                }
            }
        }
        return true
    }

}