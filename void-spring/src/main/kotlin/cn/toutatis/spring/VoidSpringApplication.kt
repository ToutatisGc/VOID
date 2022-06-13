package cn.toutatis.spring

import cn.toutatis.support.spring.annotations.VoidApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.ComponentScan

@VoidApplication
//@EnableAdminServer
@ComponentScan(basePackages = ["cn.toutatis"])
class VoidSpringApplication

fun main(args: Array<String>) {
//    val voidConfiguration = VoidContextConfigBuilder()
//        .setAutoCreateDataObject(true)
//        .build()
//    VoidContext.init(voidConfiguration)
    val applicationContext: ConfigurableApplicationContext = runApplication<VoidSpringApplication>(*args)
//    VoidContext.intervene(applicationContext)
}
