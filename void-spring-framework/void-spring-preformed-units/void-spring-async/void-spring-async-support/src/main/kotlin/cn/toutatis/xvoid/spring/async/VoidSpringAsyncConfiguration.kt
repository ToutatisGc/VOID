package cn.toutatis.xvoid.spring.async

import cn.toutatis.xvoid.spring.units.support.StandardComponentNaming
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

/**
 * @author Toutatis_Gc
 * 异步线程池配置
 */
@EnableAsync
@Configuration("VOID_SPRING_ASYNC_CONFIGURATION")
class VoidSpringAsyncConfiguration {

    companion object{
        const val VOID_ASYNC_PREFIX = "VoidAsync"
    }

    @Autowired
    private lateinit var voidSpringAsyncRejectedExecutionHandler: VoidSpringAsyncRejectedExecutionHandler

    @Bean(StandardComponentNaming.VOID_ASYNC_THREAD_POOL)
    fun voidAsyncThreadPoolTaskExecutor(): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 8
        executor.maxPoolSize = 256
        // 任务的执行不应该会超过此数量，超过队列数量应当优化任务
        executor.queueCapacity = 4096
        executor.keepAliveSeconds = 120
        executor.setThreadNamePrefix(VOID_ASYNC_PREFIX)
        executor.setWaitForTasksToCompleteOnShutdown(true)
        executor.setAwaitTerminationSeconds(60)
        executor.setRejectedExecutionHandler(voidSpringAsyncRejectedExecutionHandler)
        executor.initialize()
        return executor
    }

}