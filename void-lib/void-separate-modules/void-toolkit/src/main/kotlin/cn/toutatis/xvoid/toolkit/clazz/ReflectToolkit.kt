package cn.toutatis.xvoid.toolkit.clazz

import cn.toutatis.xvoid.common.annotations.database.AssignField
import cn.toutatis.xvoid.toolkit.formatting.JsonToolkit
import java.lang.reflect.Modifier

/**
 * Reflect toolkit
 * 反射相关工具
 * @constructor Create empty Reflect toolkit
 */
object ReflectToolkit {

    /**
     * Convert map to entity
     * 将Map类型对象转换为Java实体类
     * @param T 泛型
     * @param map 需要转换的图
     * @param entityClass 实体类Class
     * @return 转换实体类
     */
    @JvmStatic
    @Throws(Exception::class)
    fun <T> convertMapToEntity(map: Map<String?, Any?>, entityClass: Class<T>): T {
        // 获取实体类构造
        val t = entityClass.getDeclaredConstructor().newInstance()
        val fields = entityClass.declaredFields
        // 遍历对象字段
        for (field in fields) {
            // 判断是否为静态字段,跳过静态字段
            if(Modifier.isStatic(field.modifiers)){ continue }
            field.isAccessible = true
            // 获取字段上注解
            val assignField = field.getDeclaredAnnotation(AssignField::class.java)
            val fieldName = assignField?.name ?: field.name
            // 判断字段类型是否为基本类型获包装类型
            if (field.type.isPrimitive || isWrapperClass(field.type)){
                field[t] = map[fieldName]
            }else{
                // 如果是实体类型,将map中对象转为实体,并赋予当前类型
                val info = map[fieldName]
                if (info != null){
                    val parseJsonObject = JsonToolkit.parseJsonObject(info)
                    field[t] = this.convertMapToEntity(parseJsonObject,field.type)
                }
            }
        }
        return t
    }

    /**
     * Is wrapper class
     * 判断是否为包装类型
     * @param clazz class信息
     * @return 是否为包装类
     */
    @JvmStatic
    fun isWrapperClass(clazz: Class<*>): Boolean {
        return clazz == Integer::class.java || clazz == String::class.java ||
               clazz == Long::class.java || clazz == Short::class.java ||
               clazz == Byte::class.java || clazz == Float::class.java ||
               clazz == Double::class.java || clazz == Char::class.java ||
               clazz == Boolean::class.java
    }

}