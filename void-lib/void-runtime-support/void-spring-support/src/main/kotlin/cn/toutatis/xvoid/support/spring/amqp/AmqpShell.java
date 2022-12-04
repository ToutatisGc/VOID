package cn.toutatis.xvoid.support.spring.amqp;

import cn.toutatis.xvoid.common.standard.StandardFields;
//import cn.toutatis.xvoid.support.spring.amqp.entity.SystemLog;
import cn.toutatis.xvoid.support.spring.amqp.entity.SystemLog;
import cn.toutatis.xvoid.support.spring.amqp.log.LogType;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.amqp.core.*;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

import static cn.toutatis.xvoid.support.spring.amqp.XvoidSystemAmqpNamingDescription.*;
import static org.springframework.amqp.core.MessageProperties.CONTENT_TYPE_BYTES;
import static org.springframework.amqp.core.MessageProperties.CONTENT_TYPE_JSON;

/**
 * @author Toutatis_Gc
 * @date 2022/11/27 20:19
 * rabbitMQ 封装
 */
@Component
public class AmqpShell {

    private final AmqpTemplate amqpTemplate;

    /**
     * 初始化
     * @param amqpAdmin Amqp管理
     * @param amqpTemplate Amqp模板
     */
    public AmqpShell(AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
        /* 声明基础部分 */
        TopicExchange defaultTopicExchange = new TopicExchange(XVOID_SYSTEM_TOPIC_EXCHANGE);
        amqpAdmin.declareExchange(defaultTopicExchange);
        Queue logQueue = QueueBuilder.durable(XVOID_SYSTEM_LOG_QUEUE).build();
        amqpAdmin.declareQueue(logQueue);
        /*绑定声明*/
        Binding logBinding = BindingBuilder.bind(logQueue).to(defaultTopicExchange).with(XVOID_SIMPLE_LOG_ROUTE_KEY);
        amqpAdmin.declareBinding(logBinding);
    }

    /**
     * 发送日志
     * @param type 日志类型
     * @param systemLog 日志实体类
     */
    public void sendLog(LogType type, SystemLog systemLog){
        this.sendObject(StandardFields.SYSTEM_PREFIX+".LOG."+type.name().toUpperCase(),systemLog);
    }

    /**
     * 向默认交换机发送对象
     * @param routingKey 路由键
     * @param object 消息体
     */
    public void sendObject(String routingKey, Object object){
        byte[] bytes = JSON.toJSONBytes(object);
        this.sendMessage(routingKey,bytes,true);
    }

    /**
     * 发送默认交换机消息
     * @param routingKey 路由键
     * @param bytes 消息体
     * @param isObject 是否为对象,仅标注contentType
     */
    public void sendMessage(String routingKey,byte[] bytes, boolean isObject) {
        this.sendMessage(XVOID_SYSTEM_TOPIC_EXCHANGE,routingKey,bytes,isObject);
    }

    /**
     * 向指定交换机发送消息,发送结果为byte[],接收消息需要重新序列化
     * @param exchange 交换机名称
     * @param routingKey 路由键
     * @param bytes 消息体
     * @param isObject 是否为对象,仅标注contentType
     */
    public void sendMessage(String exchange,String routingKey,byte[] bytes, boolean isObject) {
        Message message = MessageBuilder
                .withBody(bytes)
                .setContentType(isObject?CONTENT_TYPE_JSON:CONTENT_TYPE_BYTES)
                .build();
        if(Validator.strIsBlank(exchange)){
            amqpTemplate.send(routingKey,message);
        }else{
            amqpTemplate.send(exchange,routingKey,message);
        }
    }

    /**
     * 转换消息
     * @param body 数据
     * @return json对象
     */
    public JSONObject serialize(byte[] body) {
        return JSON.parseObject(new String(body, StandardCharsets.UTF_8));
    }
}
