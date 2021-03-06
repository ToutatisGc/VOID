package cn.toutatis.xvoid.toolkit.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * 日志工具类
 * @author Toutatis_Gc
 */
object LoggerToolkit {

    fun getLogger(name: String): Logger{
        return LoggerFactory.getLogger(name)
    }

    fun getLogger(clazz: Class<*>): Logger{
        return LoggerFactory.getLogger(clazz)
    }

}