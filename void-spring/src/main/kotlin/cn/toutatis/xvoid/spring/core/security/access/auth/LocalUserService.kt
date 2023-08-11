package cn.toutatis.xvoid.spring.core.security.access.auth

import cn.toutatis.xvoid.spring.business.user.service.FormUserAuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class LocalUserService {

    @Autowired
    private lateinit var formUserAuthService : FormUserAuthService

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, Any>

    companion object{
        /**
         * Auth Times Key 账户登录重试次数
         */
        const val AUTH_TIMES_KEY = "VOID_RETRY_AUTH_TIMES_KEY"
    }

    fun findSimpleUser(username: String): UserDetails {
        return formUserAuthService.findSimpleUser(username)
    }

}