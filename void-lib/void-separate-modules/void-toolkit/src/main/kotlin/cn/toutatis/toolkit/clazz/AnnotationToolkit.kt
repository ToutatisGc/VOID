package cn.toutatis.toolkit.clazz

import cn.hutool.core.util.ReflectUtil

/**
 * @author Toutatis_Gc
 * 注解工具类
 */
object AnnotationToolkit {

    /**
     * @param annotation 注解实例/annotation instance
     * @param key 键名
     * 返回泛型值
     */
    fun <T> getValue(annotation: Annotation,key:String): T? {
        return ReflectUtil.invoke<T>(annotation, key)
    }

    /**
     * @param annotation 注解实例
     * @param key 键名
     * 获取注解中可空的字段中的值
     */
    fun getArrFirstString(annotation: Annotation,key:String): String?{
        val list = this.getValue<Array<String>>(annotation, key)
        return if (list!= null && list.isNotEmpty()) list[0] else null
    }

}