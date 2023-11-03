package cn.toutatis.xvoid.spring.core.security.access.auth

import cn.toutatis.redis.RedisCommonKeys
import cn.toutatis.xvoid.common.standard.AuthFields.LOGIN_ACCOUNT_LOCKED_KEY
import cn.toutatis.xvoid.common.standard.AuthFields.LOGIN_ACCOUNT_SESSION_KEY
import cn.toutatis.xvoid.common.standard.AuthFields.LOGIN_PRE_CHECK_KEY
import cn.toutatis.xvoid.orm.base.authentication.entity.RequestAuthEntity
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration
import cn.toutatis.xvoid.spring.configure.system.VoidSecurityConfiguration
import cn.toutatis.xvoid.spring.configure.system.enums.global.RunMode
import cn.toutatis.xvoid.common.standard.AuthValidationMessage
import cn.toutatis.xvoid.orm.base.authentication.service.VoidAuthService
import cn.toutatis.xvoid.toolkit.validator.Validator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.core.userdetails.UserDetails
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

    fun findSimpleUser(requestAuthEntity: RequestAuthEntity): UserDetails {
        this.preCheckAccount(requestAuthEntity)
        return formUserAuthService.findSimpleUser(requestAuthEntity)
    }

    /**
     * Pre check account 账户预检
     * @see cn.toutatis.xvoid.spring.core.security.config.handler.SecurityHandler 登录失败和成功处理
     * @param account 账户名称
     * @param authType 认证类型
     * @return 预检成功
     */
    override fun preCheckAccount(requestAuthEntity: RequestAuthEntity): Boolean {
        val account = requestAuthEntity.account
        // 用户名为空
        if (Validator.strIsBlank(account)) throwFailed(AuthValidationMessage.USERNAME_BLANK)
        // 用户名不合法
        if (!Validator.checkCNUsernameFormat(account)) throwFailed(AuthValidationMessage.USERNAME_ILLEGAL)
        val loginAccountOps = redisTemplate.boundValueOps(RedisCommonKeys.concat(LOGIN_ACCOUNT_SESSION_KEY, requestAuthEntity.sessionId))
        loginAccountOps.set(account, Duration.ofMinutes(10L))
        // 调试模式跳过检查
        if (voidGlobalConfiguration.mode == RunMode.DEBUG){ return true }
        val loginConfig = voidSecurityConfiguration.loginConfig
        // 预检用户名调用接口
        if (loginConfig.beforeLoginCheckUsername){
            val sessionOps = redisTemplate.boundValueOps(RedisCommonKeys.concat(LOGIN_PRE_CHECK_KEY,requestAuthEntity.sessionId))
            val store = sessionOps.get()
            if (store == null || account != store) {
                throwFailed(AuthValidationMessage.USERNAME_NOT_PRE_CHECK)
            }
        }
        // 允许重试
        if (loginConfig.loginRetryLimitEnabled){
            val loginLockOps = redisTemplate.boundValueOps(RedisCommonKeys.concat(LOGIN_ACCOUNT_LOCKED_KEY,account))
            val currentLoginLocked = loginLockOps.get() as String?
            if (currentLoginLocked != null){
                if (AuthValidationMessage.ACCOUNT_LOCKED_TODAY == currentLoginLocked){
                    throwFailed(currentLoginLocked)
                }else{
                    throwFailed(AuthValidationMessage.ACCOUNT_WILL_UNLOCK.format(currentLoginLocked))
                }
            }
        }
        return true
    }

}