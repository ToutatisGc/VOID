package cn.toutatis.common.annotations.database

/**
 * @author Toutatis_Gc
 * 自动将带有此注解的类映射至数据库
 */
annotation class AutoCreateDataObject(

    vararg val tableName: String = []

)
