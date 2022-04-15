package cn.toutatis.annotations

import java.lang.annotation.Inherited

@Inherited
@Target(allowedTargets = [AnnotationTarget.ANNOTATION_CLASS])
annotation class Bean()
