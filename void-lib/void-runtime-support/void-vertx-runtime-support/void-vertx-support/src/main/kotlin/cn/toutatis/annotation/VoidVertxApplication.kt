package cn.toutatis.annotation

import java.lang.annotation.Inherited

/**
 * 用来注明Vertx 的 main方法类
 */

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Inherited
annotation class VoidVertxApplication(

    val basePackages:Array<String> = []

)
