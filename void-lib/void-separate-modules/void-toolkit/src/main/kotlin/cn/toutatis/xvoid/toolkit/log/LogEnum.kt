package cn.toutatis.xvoid.toolkit.log

/**
 * 记录枚举注解值
 * @author Toutatis_Gc
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class LogEnum(val open: Boolean = true)