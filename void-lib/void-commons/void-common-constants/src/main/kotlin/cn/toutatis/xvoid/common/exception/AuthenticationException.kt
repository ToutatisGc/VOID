package cn.toutatis.xvoid.common.exception

import cn.toutatis.xvoid.common.Meta

/**
 * Authentication exception
 * 认证异常
 * @constructor
 *
 * @param message 消息
 */
class AuthenticationException(message: String) : RuntimeException("$AUTH_START_MESSAGE$message") {

    companion object{
        const val AUTH_START_MESSAGE = "[${Meta.MODULE_NAME}-AUTH]"
    }

}