package cn.toutatis.xvoid.spring.logger

import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog
import cn.toutatis.xvoid.orm.base.infrastructure.services.SystemLogService
import cn.toutatis.xvoid.spring.units.support.StandardComponentNaming
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component(StandardComponentNaming.VOID_SIMPLE_LOGGER_RECEIVER)
class VoidSpringLocalLoggerReceiver : VoidSpringLoggerReceiver{

    @Autowired
    private lateinit var systemLogService: SystemLogService

    override fun receive(log: SystemLog) {
        systemLogService.save(log)
    }
}