package cn.toutatis.annotation

import java.lang.annotation.Inherited

@Inherited
@Target(allowedTargets = [AnnotationTarget.ANNOTATION_CLASS])
annotation class Bean()
