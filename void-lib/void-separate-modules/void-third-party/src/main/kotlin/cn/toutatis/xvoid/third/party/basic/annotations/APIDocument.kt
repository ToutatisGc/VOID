package cn.toutatis.xvoid.third.party.basic.annotations

@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Target(AnnotationTarget.CLASS,AnnotationTarget.FUNCTION, AnnotationTarget.FIELD)
annotation class APIDocument(val name: String = "",
                             val description: String = "",
                             val url: String ="",
                             val version: String = "0")
