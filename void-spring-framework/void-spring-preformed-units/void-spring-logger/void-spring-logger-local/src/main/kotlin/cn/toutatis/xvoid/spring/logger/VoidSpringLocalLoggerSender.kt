package cn.toutatis.xvoid.spring.logger

import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog
import cn.toutatis.xvoid.orm.base.infrastructure.enums.LogType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class VoidSpringLocalLoggerSender : VoidSpringLoggerSender{

    @Autowired
    private lateinit var localLoggerReceiver: VoidSpringLocalLoggerReceiver

    override fun sendLog(type: LogType, systemLog: SystemLog) {
        systemLog.type = type.name
        localLoggerReceiver.receive(systemLog)
    }
}