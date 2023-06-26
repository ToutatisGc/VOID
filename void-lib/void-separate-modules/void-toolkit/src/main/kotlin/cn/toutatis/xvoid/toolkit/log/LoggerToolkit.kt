package cn.toutatis.xvoid.toolkit.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * 日志工具类
 * @author Toutatis_Gc
 */
object LoggerToolkit {

    /**
     * 获取以名称为实例的日志实例
     */
    @JvmStatic
    fun getLogger(name: String): Logger{
        return LoggerFactory.getLogger(name)
    }

    /**
     * 获取以类名为实例的日志实例
     */
    @JvmStatic
    fun getLogger(clazz: Class<*>): Logger{
        return LoggerFactory.getLogger(clazz)
    }

    @JvmStatic
    fun printModuleInfoWithMessage(module:String, message:String): String{
        return "[$module]$message"
    }

}

/**
 * Logger扩展函数
 * info方法携带module信息和消息日志
 */
fun Logger.infoWithModule(module: String,message: String){
    this.info(LoggerToolkit.printModuleInfoWithMessage(module, message))
}

/**
 * Logger扩展函数
 * warn方法携带module信息和消息日志
 * @param module 模块信息
 * @param message 日志消息
 */
fun Logger.warnWithModule(module: String,message: String){
    this.warn(LoggerToolkit.printModuleInfoWithMessage(module, message))
}

/**
 * Logger扩展函数
 * warn方法携带module信息和消息日志
 * @param module 模块信息
 * @param message 日志消息
 * @param arguments 日志格式化变量
 */
fun Logger.warnWithModule(module: String,message: String,vararg arguments: String){
    this.warn(LoggerToolkit.printModuleInfoWithMessage(module, message),arguments)
}
