package cn.toutatis.xvoid.spring.amqp;

import cn.toutatis.xvoid.common.standard.StandardFields;
import cn.toutatis.xvoid.spring.amqp.entity.SystemLog;
import cn.toutatis.xvoid.spring.amqp.log.LogEnum;
import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.*;
import org.springframework.stereotype.Component;

import static org.springframework.amqp.core.MessageProperties.CONTENT_TYPE_BYTES;
import static org.springframework.amqp.core.MessageProperties.CONTENT_TYPE_JSON;

/**
 * @author Toutatis_Gc
 * @date 2022/11/27 20:19
 * rabbitMQ 封装
 */
@Component
public class AmqpShell {

    public static final String SYSTEM_LOG_QUEUE_NAME = "XVOID-SYSTEM-LOG-QUEUE";

    public static final String SYSTEM_LOG_TOPIC_EXCHANGE_NAME = "XVOID-SYSTEM-LOG-EXCHANGE";

    private final AmqpAdmin amqpAdmin;

    private final AmqpTemplate amqpTemplate;

    public AmqpShell(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate) {
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;
        /* 声明基础要求 */
        TopicExchange logTopicExchange = new TopicExchange(SYSTEM_LOG_TOPIC_EXCHANGE_NAME);
        amqpAdmin.declareExchange(logTopicExchange);
        Queue logQueue = QueueBuilder.durable(SYSTEM_LOG_QUEUE_NAME).build();
        amqpAdmin.declareQueue(logQueue);
        Binding logBinding = BindingBuilder.bind(logQueue).to(logTopicExchange).with(StandardFields.SYSTEM_PREFIX + ".log.*");
        amqpAdmin.declareBinding(logBinding);
    }


    public void sendMessage(String routingKey,byte[] bytes, boolean isObject) {
        Message message = MessageBuilder
                .withBody(bytes)
                .setContentType(isObject?CONTENT_TYPE_JSON:CONTENT_TYPE_BYTES)
                .build();
        amqpTemplate.send(routingKey,message);
    }

    public void sendLog(LogEnum type, SystemLog systemLog){
        this.sendObject(StandardFields.SYSTEM_PREFIX+".log."+type.name().toLowerCase(),systemLog);
    }

    public void sendObject(String routingKey, Object object){
        byte[] bytes = JSON.toJSONBytes(object);
        this.sendMessage(routingKey,bytes,true);
    }

}
