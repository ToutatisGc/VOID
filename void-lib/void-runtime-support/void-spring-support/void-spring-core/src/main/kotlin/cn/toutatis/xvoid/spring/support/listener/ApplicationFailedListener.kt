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
import org.springframework.boot.context.event.ApplicationFailedEvent
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import kotlin.system.exitProcess

@Component
class ApplicationFailedListener : ApplicationListener<ApplicationFailedEvent> {

    private final val logger = LoggerToolkit.getLogger(javaClass)

    @Autowired
    private lateinit var amqpShell: AmqpShell

    override fun onApplicationEvent(event: ApplicationFailedEvent) {
        // TODO 失败处理
        System.err.println(event)
        exitProcess(0)
    }

}