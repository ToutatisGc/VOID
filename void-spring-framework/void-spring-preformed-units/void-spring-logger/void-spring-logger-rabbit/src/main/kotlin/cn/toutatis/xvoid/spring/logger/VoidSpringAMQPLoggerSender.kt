package cn.toutatis.xvoid.spring.logger

import cn.toutatis.xvoid.common.standard.StandardFields
import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog
import cn.toutatis.xvoid.orm.base.infrastructure.enums.LogType
import cn.toutatis.xvoid.spring.amqp.AMQPShell
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class VoidSpringAMQPLoggerSender : VoidSpringLoggerSender {

    @Autowired
    private lateinit var amqpShell: AMQPShell

    /**
     * 发送日志
     * @param type 日志类型
     * @param systemLog 日志实体类
     */
    override fun sendLog(type: LogType, systemLog: SystemLog) {
        amqpShell.sendObject(StandardFields.SYSTEM_PREFIX + ".LOG." + type.name.uppercase(), systemLog)
    }
}