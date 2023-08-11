package cn.toutatis.xvoid.spring.core.security.access.auth

import cn.toutatis.xvoid.common.exception.AuthenticationException
import cn.toutatis.xvoid.spring.business.user.service.FormUserAuthService
import cn.toutatis.xvoid.spring.configure.system.VoidSecurityConfiguration
import cn.toutatis.xvoid.toolkit.validator.Validator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class LocalUserService : VoidAuthService {

    @Autowired
    private lateinit var formUserAuthService : FormUserAuthService

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, Any>

    @Autowired
    private lateinit var voidSecurityConfiguration: VoidSecurityConfiguration

    companion object{
        /**
         * Auth Times Key 账户登录重试次数
         */
        const val LOGIN_RETRY_TIMES_KEY = "VOID_RETRY_AUTH_TIMES"
    }

    fun findSimpleUser(username: String): UserDetails {
        this.preCheck(username)
        return formUserAuthService.findSimpleUser(username)
    }

    override fun preCheck(username: String): Boolean {
        if (Validator.strIsBlank(username)) throwFailed("用户名不得为空")
        if (!Validator.checkCNUsername(username)) throwFailed("用户名不合法")
        return true
    }

}