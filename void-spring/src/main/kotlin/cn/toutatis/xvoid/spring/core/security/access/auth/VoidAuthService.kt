package cn.toutatis.xvoid.spring.core.security.access.auth

import cn.toutatis.xvoid.common.exception.AuthenticationException
import kotlin.jvm.Throws

/**
 * Void auth service
 * 认证服务接口
 * @constructor Create empty Void auth service
 */
interface VoidAuthService {

    /**
     * Pre check
     * 用户名预检查
     * @param username
     * @return
     */
    fun preCheck(username:String):Boolean

    /**
     * Throw failed
     * 抛出异常
     * @param message
     */
    @Throws(AuthenticationException::class)
    fun throwFailed(message:String): Unit = throw AuthenticationException(message)

}