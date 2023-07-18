package cn.toutatis.xvoid.spring

import cn.toutatis.xvoid.spring.annotations.application.VoidApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@VoidApplication
@EnableRedisHttpSession
class VoidSpringApplication

fun main(args: Array<String>) {
//    val voidConfiguration = VoidContextConfigBuilder()
//        .setAutoCreateDataObject(true)
//        .build()
//    VoidContext.init(voidConfiguration)
    val applicationContext: ConfigurableApplicationContext = runApplication<VoidSpringApplication>(*args)
//    VoidContext.intervene(applicationContext)
}