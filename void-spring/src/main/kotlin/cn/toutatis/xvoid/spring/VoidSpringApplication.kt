package cn.toutatis.xvoid.spring

import cn.toutatis.xvoid.support.spring.annotations.VoidApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.ComponentScan

@VoidApplication
//@EnableAdminServer
@ComponentScan(basePackages = ["cn.toutatis"])
@EntityScan(basePackages=["cn.toutatis"])
//@MapperScan(basePackages = ["cn.toutatis"])
class VoidSpringApplication

fun main(args: Array<String>) {
//    val voidConfiguration = VoidContextConfigBuilder()
//        .setAutoCreateDataObject(true)
//        .build()
//    VoidContext.init(voidConfiguration)
    val applicationContext: ConfigurableApplicationContext = runApplication<VoidSpringApplication>(*args)
//    VoidContext.intervene(applicationContext)
}

