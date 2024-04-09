package cn.toutatis.xvoid.spring.async

import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog
import cn.toutatis.xvoid.orm.base.infrastructure.enums.LogType
import cn.toutatis.xvoid.spring.logger.VoidSpringLoggerSender
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.warnWithModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadPoolExecutor

/**
 * 异步线程池拒绝策略
 * @author Toutatis_Gc
 */
@Component("VOID_SPRING_ASYNC_REJECTED_EXECUTION_HANDLER")
class VoidSpringAsyncRejectedExecutionHandler:RejectedExecutionHandler {

    private val logger = LoggerToolkit.getLogger(javaClass)

    @Autowired
    private lateinit var voidSpringLoggerSender: VoidSpringLoggerSender

    override fun rejectedExecution(r: Runnable, executor: ThreadPoolExecutor) {
        if (!executor.isShutdown){
            logger.warnWithModule(Meta.MODULE_NAME, "线程池已满，任务被线程池拒绝执行,将在当前调度中执行")
            val systemLog = SystemLog()
            // TODO 日志支持
            systemLog.subType = "ASYNC"
            voidSpringLoggerSender.sendLog(LogType.SYSTEM,systemLog)
            r.run()
        }
    }

}