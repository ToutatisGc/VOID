package cn.toutatis.xvoid.spring.support.listener

import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import org.springframework.boot.context.event.ApplicationFailedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import kotlin.system.exitProcess

@Component
class ApplicationFailedListener : ApplicationListener<ApplicationFailedEvent> {

    private final val logger = LoggerToolkit.getLogger(javaClass)

    override fun onApplicationEvent(event: ApplicationFailedEvent) {
        // TODO 失败处理
        try {
            System.err.println(event)
        }catch (e:Exception){

        }finally {
            exitProcess(0)
        }
    }

}