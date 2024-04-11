package cn.toutatis.xvoid.spring.logger

import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog
import cn.toutatis.xvoid.orm.base.infrastructure.enums.LogType
import cn.toutatis.xvoid.spring.units.support.StandardComponentNaming
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.infoWithModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component(StandardComponentNaming.VOID_SIMPLE_LOGGER_SENDER)
class VoidSpringLocalLoggerSender : VoidSpringLoggerSender{

    @Autowired
    private lateinit var localLoggerReceiver: VoidSpringLocalLoggerReceiver

    private val logger = LoggerToolkit.getLogger(javaClass)

    constructor(){
        logger.infoWithModule("VOID-SPRING","本地日志发送器已启动")
    }

    override fun sendLog(type: LogType, systemLog: SystemLog) {
        systemLog.type = type.name
        localLoggerReceiver.receive(systemLog)
    }
}