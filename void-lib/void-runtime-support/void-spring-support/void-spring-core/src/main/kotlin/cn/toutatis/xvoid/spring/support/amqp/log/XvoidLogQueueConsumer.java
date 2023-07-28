package cn.toutatis.xvoid.spring.support.amqp.log;

import cn.toutatis.xvoid.orm.base.infrastructure.enums.LogType;
import cn.toutatis.xvoid.spring.support.amqp.AmqpShell;
import cn.toutatis.xvoid.spring.support.amqp.XvoidSystemAmqpNamingDescription;
import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog;
import cn.toutatis.xvoid.orm.base.infrastructure.services.SystemLogServiceImpl;
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
            type = receivedRoutingKey.substring(receivedRoutingKey.lastIndexOf(".")+1);
        }
        logType = LogType.valueOf(type);
        SystemLog systemLog = JSON.toJavaObject(body, SystemLog.class);
        systemLog.setType(logType.name());
        try{
            systemLogService.save(systemLog);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
