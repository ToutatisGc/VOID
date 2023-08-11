package cn.toutatis.xvoid.common.exception

/**
 * Authentication exception
 * 认证异常
 * @constructor
 *
 * @param message 消息
 */
class AuthenticationException(message: String) : RuntimeException("${AUTH_START_MESSAGE}$message") {

    companion object{
        const val AUTH_START_MESSAGE = "XVOID-AUTH:"
    }

}