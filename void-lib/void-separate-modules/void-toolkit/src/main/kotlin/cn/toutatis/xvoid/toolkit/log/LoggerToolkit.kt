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

    /**
     * 返回格式化日志信息
     * 一般模块目录下都有一个VoidModuleInfo类,其中包含了MODULE_NAME字段模块名称
     * @param module 模块名称
     * @param message 日志消息
     * @see cn.toutatis.xvoid.toolkit.VoidModuleInfo
     */
    @JvmStatic
    fun infoWithModule(module:String, message:String): String{
        return "[$module]$message"
    }

    /**
     * 返回格式化日志信息
     * 一般模块目录下都有一个VoidModuleInfo类,其中包含了MODULE_NAME字段模块名称
     * @param module 模块名称
     * @param subModule 子模块(由各个文件夹下的功能简称作为子模块名称,不强制定义)
     * @param message 日志消息
     * @see cn.toutatis.xvoid.toolkit.VoidModuleInfo 模块信息
     */
    @JvmStatic
    fun infoWithModule(module:String, subModule:String, message:String): String{
        return "[$module-$subModule]$message"
    }

}

/**
 * Logger扩展函数
 * info方法携带module信息和消息日志
 */
fun Logger.infoWithModule(module: String,message: String):String{
    val log = LoggerToolkit.infoWithModule(module, message)
    this.info(log)
    return log
}

/**
 * Logger扩展函数
 * warn方法携带module信息和消息日志
 * @param module 模块信息
 * @param message 日志消息
 */
fun Logger.warnWithModule(module: String,message: String):String{
    val log = LoggerToolkit.infoWithModule(module, message)
    this.warn(log)
    return log
}

/**
 * Logger扩展函数
 * error方法携带module信息和消息日志
 */
fun Logger.errorWithModule(module: String,message: String):String{
    val log = LoggerToolkit.infoWithModule(module, message)
    this.error(log)
    return log
}

/**
 * Logger扩展函数
 * info方法携带module信息和消息日志
 */
fun Logger.infoWithModule(module: String,subModule: String,message: String):String{
    val log = LoggerToolkit.infoWithModule(module,subModule, message)
    this.info(log)
    return log
}

/**
 * Logger扩展函数
 * warn方法携带module信息和消息日志
 */
fun Logger.warnWithModule(module: String,subModule: String,message: String):String{
    val log = LoggerToolkit.infoWithModule(module,subModule, message)
    this.warn(log)
    return log
}

/**
 * Logger扩展函数
 * error方法携带module信息和消息日志
 */
fun Logger.errorWithModule(module: String,subModule: String,message: String):String{
    val log = LoggerToolkit.infoWithModule(module,subModule, message)
    this.error(log)
    return log
}

/**
 * Logger扩展函数
 * warn方法携带module信息和消息日志
 * @param module 模块信息
 * @param message 日志消息
 * @param arguments 日志格式化变量
 */
fun Logger.warnWithModule(module: String,message: String,vararg arguments: String):String{
    val log = LoggerToolkit.infoWithModule(module, message)
    this.warn(log,arguments)
    return log
}
