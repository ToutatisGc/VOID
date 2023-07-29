package cn.toutatis.xvoid.spring.support.listener

import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog
import cn.toutatis.xvoid.orm.base.infrastructure.enums.LogType
import cn.toutatis.xvoid.spring.support.amqp.AmqpShell
import cn.toutatis.xvoid.toolkit.constant.Time
import com.alibaba.fastjson.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class ApplicationStartListener : ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private lateinit var amqpShell: AmqpShell

    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        /*TODO 启动事件*/
        val systemLog = SystemLog()
        systemLog.intro = "SpringBootApplication初始化"
        systemLog.user = "SYSTEM"
        val obj = JSONObject(3,true)
        obj["applicationName"] = event.applicationContext.id
        obj["mainClass"] = event.springApplication.mainApplicationClass
        obj["startDate"] = Time.getCurrentTimeByLong(event.timestamp)
        systemLog.details = obj.toJSONString()
        amqpShell.sendLog(LogType.SYSTEM,systemLog)
        System.err.println(event.timestamp)
    }
}