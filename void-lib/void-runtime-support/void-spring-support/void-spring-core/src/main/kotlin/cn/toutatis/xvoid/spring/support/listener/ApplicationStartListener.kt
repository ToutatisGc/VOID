package cn.toutatis.xvoid.spring.support.listener

import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog
import cn.toutatis.xvoid.orm.base.infrastructure.enums.LogType
import cn.toutatis.xvoid.spring.support.Meta
import cn.toutatis.xvoid.spring.support.amqp.AmqpShell
import cn.toutatis.xvoid.toolkit.constant.Time
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.infoWithModule
import com.alibaba.fastjson.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class ApplicationStartListener : ApplicationListener<ApplicationStartedEvent> {

    private final val logger = LoggerToolkit.getLogger(javaClass)

    @Autowired
    private lateinit var amqpShell: AmqpShell

    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        /*TODO 启动事件*/
        val systemLog = SystemLog()
        systemLog.intro = "SpringBootApplication初始化"
        systemLog.user = "SYSTEM"
        val id = event.applicationContext.id
        val obj = JSONObject(3,true)
        obj["applicationName"] = id
        obj["mainClass"] = event.springApplication.mainApplicationClass
        obj["startDate"] = Time.getCurrentTimeByLong(event.timestamp)
        amqpShell.sendLog(LogType.SYSTEM,systemLog)
        logger.infoWithModule(Meta.MODULE_NAME,"项目[$id]已于[${Time.currentTime}]启动,类加载已完成,等待DispatcherServlet介入...")
    }
}