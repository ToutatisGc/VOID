package cn.toutatis.xvoid.spring.core.security.access.auth

import cn.toutatis.xvoid.common.exception.AuthenticationException
import cn.toutatis.xvoid.spring.core.security.access.AuthType
import com.alibaba.fastjson.JSONObject
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
     * @param account 用户名
     * @param authType 账户类型
     * @return 账户是否存在
     */
    fun preCheckAccount(identity:JSONObject):Boolean

    /**
     * Throw failed
     * 抛出异常
     * @param message 异常消息
     */
    @Throws(AuthenticationException::class)
    fun throwFailed(message:String): Unit = throw AuthenticationException(message)

}