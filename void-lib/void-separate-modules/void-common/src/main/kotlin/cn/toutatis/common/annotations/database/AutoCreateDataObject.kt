package cn.toutatis.common.annotations.database

import java.lang.annotation.Inherited

/**
 * @author Toutatis_Gc
 * 自动将带有此注解的类映射至数据库
 */
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
annotation class AutoCreateDataObject(

    val tableName: Array<String> = []


)


