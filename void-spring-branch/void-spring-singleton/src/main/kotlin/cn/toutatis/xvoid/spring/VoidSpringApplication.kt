package cn.toutatis.xvoid.spring

import cn.toutatis.xvoid.common.standard.StandardFields
import cn.toutatis.xvoid.project.spring.VoidSpringContext
import cn.toutatis.xvoid.spring.annotations.application.VoidApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.session.FlushMode
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@VoidApplication
@EnableRedisHttpSession(redisNamespace = StandardFields.SYSTEM_PREFIX+":SESSION", flushMode = FlushMode.IMMEDIATE)
class VoidSpringApplication

fun main(args: Array<String>) {
    VoidSpringContext(runApplication<VoidSpringApplication>(*args)).intervene()
}