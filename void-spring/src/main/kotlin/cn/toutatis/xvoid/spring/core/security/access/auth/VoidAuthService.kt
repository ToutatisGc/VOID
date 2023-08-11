package cn.toutatis.xvoid.spring.core.security.access.auth

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

}