package cn.toutatis.xvoid.spring.logger

import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog
import cn.toutatis.xvoid.orm.base.infrastructure.enums.LogType

/**
 * 操作日志发送器接口定义了发送操作日志的方法。
 * 该接口允许系统中的不同模块通过实现 [VoidSpringLoggerSender] 接口来发送操作日志。
 * @author Toutatis_Gc
 */
interface VoidSpringLoggerSender {

    /**
     * 发送操作日志的方法。
     *
     * @param type      操作日志的类型。枚举类型 [LogType]，用于表示日志的不同类型，例如信息、警告、错误等。
     * @param systemLog 要发送的系统日志对象。[SystemLog] 是一个包含了操作日志相关信息的数据类。
     */
    fun sendLog(type: LogType, systemLog: SystemLog)

}


