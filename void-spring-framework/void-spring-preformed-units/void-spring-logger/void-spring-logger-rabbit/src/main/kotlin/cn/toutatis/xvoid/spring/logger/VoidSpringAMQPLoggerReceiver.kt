package cn.toutatis.xvoid.spring.logger

import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog
import cn.toutatis.xvoid.orm.base.infrastructure.enums.LogType
import cn.toutatis.xvoid.orm.base.infrastructure.services.SystemLogService
import cn.toutatis.xvoid.spring.amqp.AMQPShell
import cn.toutatis.xvoid.spring.amqp.XvoidSystemAmqpNamingDescription
import cn.toutatis.xvoid.toolkit.validator.Validator.strIsBlank
import com.alibaba.fastjson.JSON
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

/**
 * @author Toutatis_Gc
 * @date 2022/11/28 14:47
 * 日志消费者
 */
@Component
class VoidSpringAMQPLoggerReceiver(private val amqpShell: AMQPShell, private val systemLogService: SystemLogService) : VoidSpringLoggerReceiver{

    @RabbitListener(queues = [XvoidSystemAmqpNamingDescription.XVOID_SYSTEM_LOG_QUEUE])
    fun receiveLogMessage(message: Message) {
        val body = amqpShell.serialize(message.body)
        var type = body.getString("type")
        if (strIsBlank(type)) {
            val receivedRoutingKey = message.messageProperties.receivedRoutingKey
            type = receivedRoutingKey.substring(receivedRoutingKey.lastIndexOf(".") + 1)
        }
        val logType: LogType = LogType.valueOf(type!!)
        val systemLog = JSON.toJavaObject(body, SystemLog::class.java)
        systemLog.type = logType.name
        this.receive(systemLog)
    }

    override fun receive(log: SystemLog) {
        systemLogService.save(log)
    }
}
