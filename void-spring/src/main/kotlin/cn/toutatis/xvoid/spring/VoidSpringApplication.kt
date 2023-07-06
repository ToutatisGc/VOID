package cn.toutatis.xvoid.spring

import cn.toutatis.xvoid.support.spring.annotations.VoidApplication
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@VoidApplication
@ComponentScan(basePackages = [VoidModuleInfo.BASE_SCAN_PACKAGE])
@EntityScan(basePackages=[VoidModuleInfo.BASE_SCAN_PACKAGE])
@MapperScan(VoidModuleInfo.BASE_SCAN_PACKAGE+".**")
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