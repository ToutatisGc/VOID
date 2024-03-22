package cn.toutatis.xvoid.spring.logger

import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog

/**
 * 操作日志接收器接口定义了接收操作日志的方法。
 * 该接口允许系统中的不同模块通过实现 [VoidSpringLoggerReceiver] 接口来接收操作日志。
 *
 * @author Toutatis_Gc
 */
interface VoidSpringLoggerReceiver {

    /**
     * 接收操作日志的方法。
     *
     * @param log 要接收的系统日志对象。 [SystemLog] 包含了操作日志相关信息的数据类。
     */
    fun receive(log: SystemLog)
}
