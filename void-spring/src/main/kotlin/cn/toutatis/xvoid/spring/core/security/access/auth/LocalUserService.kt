package cn.toutatis.xvoid.spring.core.security.access.auth

import cn.toutatis.redis.RedisCommonKeys
import cn.toutatis.xvoid.common.standard.AuthFields.LOGIN_ACCOUNT_LOCKED_KEY
import cn.toutatis.xvoid.common.standard.AuthFields.LOGIN_ACCOUNT_SESSION_KEY
import cn.toutatis.xvoid.common.standard.AuthFields.LOGIN_PRE_CHECK_KEY
import cn.toutatis.xvoid.common.standard.AuthFields.LOGIN_RETRY_TIMES_KEY
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
import org.springframework.security.web.firewall.FirewalledRequest
import org.springframework.stereotype.Service
import java.time.Duration
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

    fun findSimpleUser(account: String): UserDetails {
        this.preCheckAccount(account,AuthType.ACCOUNT_NORMAL)
        return formUserAuthService.findSimpleUser(account)
    }

    /**
     * Pre check account 账户预检
     * @see cn.toutatis.xvoid.spring.core.security.config.handler.SecurityHandler 登录失败和成功处理
     * @param account 账户名称
     * @param authType 认证类型
     * @return 预检成功
     */
    override fun preCheckAccount(account: String, authType: AuthType): Boolean {
        // 用户名为空
        if (Validator.strIsBlank(account)) throwFailed(ValidationMessage.USERNAME_BLANK)
        // 用户名不合法
        if (!Validator.checkCNUsername(account)) throwFailed(ValidationMessage.USERNAME_ILLEGAL)
        val loginAccountOps = redisTemplate.boundValueOps(RedisCommonKeys.concat(LOGIN_ACCOUNT_SESSION_KEY, httpServletRequest.session.id))
        loginAccountOps.set(account, Duration.ofMinutes(10L))
        // 调试模式跳过检查
        if (voidGlobalConfiguration.mode == RunMode.DEBUG){ return true }
        val loginConfig = voidSecurityConfiguration.loginConfig
        // 预检用户名调用接口
        if (loginConfig.beforeLoginCheckUsername){
            val sessionOps = redisTemplate.boundValueOps(RedisCommonKeys.concat(LOGIN_PRE_CHECK_KEY,httpServletRequest.session.id))
            val store = sessionOps.get()
            if (store == null || account != store) {
                throwFailed(ValidationMessage.USERNAME_NOT_PRE_CHECK)
            }
        }
        // 允许重试
        if (loginConfig.loginRetryLimitEnabled){
            val loginLockOps = redisTemplate.boundValueOps(RedisCommonKeys.concat(LOGIN_ACCOUNT_LOCKED_KEY,account))
            val currentLoginLocked = loginLockOps.get() as String?
            if (currentLoginLocked != null){
                if (ValidationMessage.ACCOUNT_LOCKED_TODAY == currentLoginLocked){
                    throwFailed(currentLoginLocked)
                }else{
                    throwFailed(ValidationMessage.ACCOUNT_WILL_UNLOCK.format(currentLoginLocked))
                }
            }
        }
        return true
    }

}