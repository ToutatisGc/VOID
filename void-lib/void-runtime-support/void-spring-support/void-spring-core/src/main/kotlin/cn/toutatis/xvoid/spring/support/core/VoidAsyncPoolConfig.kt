package cn.toutatis.xvoid.spring.support.core

import cn.toutatis.xvoid.common.standard.StandardComponentPool
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.ThreadPoolExecutor

/**
 * @author Toutatis_Gc
 * 异步线程池配置
 */
@Configuration
open class VoidAsyncPoolConfig {

    @Bean(StandardComponentPool.VOID_ASYNC_THREAD_POOL)
    open fun voidAsyncThreadPoolTaskExecutor(): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 20
        executor.maxPoolSize = 200
        executor.setQueueCapacity(25)
        executor.keepAliveSeconds = 200
        executor.setThreadNamePrefix("VoidAsync")
        executor.setWaitForTasksToCompleteOnShutdown(true)
        executor.setAwaitTerminationSeconds(60)
        executor.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())
        executor.initialize()
        return executor
    }
}