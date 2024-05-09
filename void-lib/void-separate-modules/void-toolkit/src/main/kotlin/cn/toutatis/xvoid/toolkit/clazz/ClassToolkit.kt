package cn.toutatis.xvoid.toolkit.clazz

import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.warnWithModule


/**
 * Class toolkit
 * Class 工具类
 * @author Toutatis_Gc
 */
object ClassToolkit {

    private val LOGGER = LoggerToolkit.getLogger(javaClass)

    /**
     * Is running from jar
     * 判断Class是否是否从jar运行
     * @param clazz Class类
     * @return true: 是jar运行 false: 非jar运行
     */
    @JvmStatic
    fun isRunningFromJar(clazz: Class<*>): Boolean {
        val protectionDomain = clazz.getProtectionDomain()
        val codeSource = protectionDomain.codeSource
        return codeSource != null && codeSource.location.toString().endsWith(".jar")
    }

    /**
     * 将对象数组转换为类数组。
     * 这个函数遍历输入的任意对象数组，获取每个对象的Java类信息，并将这些类信息组成一个新的类数组返回。
     * @param array 输入的任意对象数组。
     * @return 返回一个包含输入数组每个元素对应类信息的类数组。
     */
    @JvmStatic
    fun castObjectArray2ClassArray(array: List<Any?>): Array<Class<*>> {
        val classes = arrayOfNulls<Class<*>>(array.size)
        for (i in array.indices) {
            if (array[i] == null){
                LOGGER.warnWithModule("ClassToolkit","数组中存在null元素，已自动转换为Object类型")
                classes[i] = Object::class.java
            }else{
                classes[i] = array[i]!!.javaClass
            }
        }
        @Suppress("UNCHECKED_CAST")
        return classes as Array<Class<*>>
    }

}