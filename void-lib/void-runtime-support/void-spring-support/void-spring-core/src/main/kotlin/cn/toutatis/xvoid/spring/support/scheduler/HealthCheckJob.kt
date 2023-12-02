package cn.toutatis.xvoid.spring.support.scheduler

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.health.HealthEndpoint
import org.springframework.stereotype.Component

@Component
class HealthCheckJob : Job{

    @Autowired
    private lateinit var healthEndpoint: HealthEndpoint

    override fun execute(context: JobExecutionContext) {
        TODO("Not yet implemented")
//        VoidSpringToolkit
    }
}