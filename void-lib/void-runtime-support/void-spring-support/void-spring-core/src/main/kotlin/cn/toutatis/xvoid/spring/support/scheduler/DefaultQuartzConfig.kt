package cn.toutatis.xvoid.spring.support.scheduler

import org.quartz.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * Default quartz config
 * @constructor Create empty Default quartz config
 */
@Configuration
class DefaultQuartzConfig {

    @Bean
    fun myJobDetail(): JobDetail {
        return JobBuilder.newJob(HealthCheckJob::class.java)
            .storeDurably()
            .withIdentity("myJob")
            .withDescription("Invoke MyJob")
            .build()
    }

    @Bean
    fun myJobTrigger(): Trigger {

        val scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
            .withIntervalInSeconds(10) // 每隔60秒执行一次
            .repeatForever()
        return TriggerBuilder.newTrigger()
            .forJob(myJobDetail())
            .withIdentity("myTrigger")
            .withDescription("Invoke MyJob")
            .withSchedule(scheduleBuilder)
            .build()
    }
}