package cn.toutatis.xvoid.spring.core.security.access.auth

import cn.toutatis.xvoid.spring.business.user.service.FormUserAuthService
import cn.toutatis.xvoid.spring.configure.system.VoidSecurityConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

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
        const val AUTH_TIMES_KEY = "VOID_RETRY_AUTH_TIMES_KEY"
    }

    fun findSimpleUser(username: String): UserDetails {

        return formUserAuthService.findSimpleUser(username)
    }

    override fun preCheck(username: String): Boolean {
        TODO("Not yet implemented")
    }

}