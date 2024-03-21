package cn.toutatis.xvoid.toolkit.clazz

import cn.toutatis.xvoid.common.exception.base.VoidRuntimeException

/**
 * Exception toolkit
 * 异常工具类
 * @constructor Create empty Exception toolkit
 */
object ExceptionToolkit {

    /**
     * Throw exception
     * 抛出异常
     * @param T 异常类型
     * @param exception 异常
     * @param message 消息
     */
    @Throws
    fun <T: VoidRuntimeException> throwException(exception: Class<T>, message: String? = null){
        throw if (message != null){
            exception.getConstructor(String::class.java).newInstance(message)
        }else {
            exception.getConstructor().newInstance()
        }
    }

}