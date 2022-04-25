package cn.toutatis.spring

import cn.toutatis.support.spring.annotations.VoidApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@VoidApplication
@ComponentScan(basePackages = ["cn.toutatis"])
class VoidSpringApplication

fun main(args: Array<String>) {
    runApplication<VoidSpringApplication>(*args)
}
