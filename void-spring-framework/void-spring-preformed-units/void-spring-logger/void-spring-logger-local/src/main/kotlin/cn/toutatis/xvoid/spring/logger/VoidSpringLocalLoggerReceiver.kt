package cn.toutatis.xvoid.spring.logger

import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog
import cn.toutatis.xvoid.orm.base.infrastructure.services.SystemLogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class VoidSpringLocalLoggerReceiver : VoidSpringLoggerReceiver{

    @Autowired
    private lateinit var systemLogService: SystemLogService

    override fun receive(log: SystemLog) {
        systemLogService.save(log)
    }
}