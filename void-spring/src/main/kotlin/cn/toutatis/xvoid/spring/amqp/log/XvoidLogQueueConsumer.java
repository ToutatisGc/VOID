package cn.toutatis.xvoid.spring.amqp.log;

import cn.toutatis.xvoid.spring.amqp.AmqpShell;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author Toutatis_Gc
 * @date 2022/11/28 14:47
 * 消费日志产生
 */
@Component
public class XvoidLogQueueConsumer {

    @RabbitListener(queues = AmqpShell.SYSTEM_LOG_QUEUE_NAME)
    public void receiveLogMessage(Message message){
        JSONObject messageBody = JSON.parseObject(new String(message.getBody(), StandardCharsets.UTF_8));
        System.err.println(message);
    }

}
