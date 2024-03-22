package cn.toutatis.xvoid.spring.support.listener

import cn.toutatis.xvoid.spring.support.Meta
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.errorWithModule
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

        }catch (e:Exception){
            e.printStackTrace()
            logger.errorWithModule(Meta.MODULE_NAME,"应用启动失败。错误原因：[${e.message}]")
        }finally {
            exitProcess(0)
        }
    }

}