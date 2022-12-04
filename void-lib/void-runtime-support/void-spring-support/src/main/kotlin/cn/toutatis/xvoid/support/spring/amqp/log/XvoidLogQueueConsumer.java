package cn.toutatis.xvoid.support.spring.amqp.log;

import cn.toutatis.xvoid.support.spring.amqp.AmqpShell;
import cn.toutatis.xvoid.support.spring.amqp.XvoidSystemAmqpNamingDescription;
//import cn.toutatis.xvoid.support.spring.amqp.entity.SystemLog;
//import cn.toutatis.xvoid.support.spring.amqp.persistence.SystemLogMapper;
//import cn.toutatis.xvoid.support.spring.amqp.service.SystemLogService;
import cn.toutatis.xvoid.support.spring.amqp.entity.SystemLog;
import cn.toutatis.xvoid.support.spring.amqp.service.SystemLogService;
import cn.toutatis.xvoid.support.spring.amqp.service.impl.SystemLogServiceImpl;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Toutatis_Gc
 * @date 2022/11/28 14:47
 * 日志消费者
 */
@Component
public class XvoidLogQueueConsumer {

    @Autowired
    private AmqpShell amqpShell;

    @Autowired
    private SystemLogServiceImpl systemLogService;

    @RabbitListener(queues = XvoidSystemAmqpNamingDescription.XVOID_SYSTEM_LOG_QUEUE)
    public void receiveLogMessage(Message message){
        JSONObject body = amqpShell.serialize(message.getBody());
        String type = body.getString("type");
        LogType logType;
        if (Validator.strIsBlank(type)) {
            String receivedRoutingKey = message.getMessageProperties().getReceivedRoutingKey();
            type = receivedRoutingKey.substring(receivedRoutingKey.lastIndexOf("."));
        }
        logType = LogType.valueOf(type);
        SystemLog systemLog = JSON.toJavaObject(body, SystemLog.class);
        systemLog.setType(logType.name());
        try{
            systemLogService.save(systemLog);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.err.println(systemLog);
    }

}
