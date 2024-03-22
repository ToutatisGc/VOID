package cn.toutatis.xvoid.spring.amqp

import cn.toutatis.xvoid.spring.amqp.XvoidSystemAmqpNamingDescription.XVOID_SIMPLE_LOG_ROUTE_KEY
import cn.toutatis.xvoid.spring.amqp.XvoidSystemAmqpNamingDescription.XVOID_SYSTEM_LOG_QUEUE
import cn.toutatis.xvoid.spring.amqp.XvoidSystemAmqpNamingDescription.XVOID_SYSTEM_TOPIC_EXCHANGE
import cn.toutatis.xvoid.toolkit.validator.Validator.strIsBlank
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import org.springframework.amqp.core.*
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * rabbitMQ 封装
 * @author Toutatis_Gc
 * @date 2022/11/27 20:19
 */
@Component
class AMQPShell(amqpAdmin: AmqpAdmin, private val amqpTemplate: AmqpTemplate) {

    /**
     * 初始化
     * @param amqpAdmin Amqp管理
     * @param amqpTemplate Amqp模板
     */
    init {
        /* 声明基础部分 */
        val defaultTopicExchange = TopicExchange(XVOID_SYSTEM_TOPIC_EXCHANGE)
        amqpAdmin.declareExchange(defaultTopicExchange)
        val logQueue = QueueBuilder.durable(XVOID_SYSTEM_LOG_QUEUE).build()
        amqpAdmin.declareQueue(logQueue)
        /*绑定声明*/
        val logBinding: Binding =
            BindingBuilder.bind(logQueue).to(defaultTopicExchange).with(XVOID_SIMPLE_LOG_ROUTE_KEY)
        amqpAdmin.declareBinding(logBinding)
    }

    /**
     * 向默认交换机发送对象
     * @param routingKey 路由键
     * @param object 消息体
     */
    fun sendObject(routingKey: String, `object`: Any) {
        val bytes = JSON.toJSONBytes(`object`)
        this.sendMessage(routingKey, bytes, true)
    }

    /**
     * 发送默认交换机消息
     * @param routingKey 路由键
     * @param bytes 消息体
     * @param isObject 是否为对象,仅标注contentType
     */
    fun sendMessage(routingKey: String, bytes: ByteArray, isObject: Boolean) {
        this.sendMessage(XVOID_SYSTEM_TOPIC_EXCHANGE, routingKey, bytes, isObject)
    }

    /**
     * 向指定交换机发送消息,发送结果为byte[],接收消息需要重新序列化
     * @param exchange 交换机名称
     * @param routingKey 路由键
     * @param bytes 消息体
     * @param isObject 是否为对象,仅标注contentType
     */
    fun sendMessage(exchange: String, routingKey: String, bytes: ByteArray, isObject: Boolean) {
        val message = MessageBuilder
            .withBody(bytes)
            .setContentType(if (isObject) MessageProperties.CONTENT_TYPE_JSON else MessageProperties.CONTENT_TYPE_BYTES)
            .build()
        if (strIsBlank(exchange)) {
            amqpTemplate.send(routingKey, message)
        } else {
            amqpTemplate.send(exchange, routingKey, message)
        }
    }

    /**
     * 转换消息
     * @param body 数据
     * @return json对象
     */
    fun serialize(body: ByteArray): JSONObject {
        return JSON.parseObject(String(body, StandardCharsets.UTF_8))
    }
}
