package cn.toutatis.xvoid.toolkit.validator

import cn.toutatis.xvoid.toolkit.constant.Regex
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * @author Toutatis_Gc
 * @date 2022/10/5 23:28
 * 参数校验器
 */
public object Validator {

    private val logger = LoggerToolkit.getLogger(Validator::class.java)

    private val numPattern = Pattern.compile(Regex.NUMBER_REGEX)

    /**
     * @param o 可转String的object
     * @return 返回自然情况下是否为空
     */
    fun strNotBlank(o: Any?): Boolean {
        if(o == null){ return false }
        val s = o.toString().trim()
        return "" != s && "null" != s.lowercase()
    }

    /**
     * @param o 可转String的object
     * @return 返回是否是空
     */
    fun strIsBlank(o: Any?): Boolean {
        val s = o.toString() ?: return true
        val trim = s.trim { it <= ' ' }.lowercase()
        return trim.isEmpty() || "null" == trim || "undefined" == trim
    }

    /**
     * 对象字段为null
     * @param obj 实体类
     * @return 字段是否为空
     */
    fun objFieldNotNull(obj: Any?): Boolean {
        if (obj != null) {
            val objClass: Class<*> = obj.javaClass
            val declaredMethods: Array<Method> = objClass.declaredMethods
            if (declaredMethods.isNotEmpty()) {
                var methodCount = 0 // get 方法数量
                var nullValueCount = 0 // 结果为空
                for (declaredMethod in declaredMethods) {
                    val name: String = declaredMethod.name
                    if (name.startsWith("get") || name.startsWith("is")) {
                        methodCount += 1
                        try {
                            val invoke: Any = declaredMethod.invoke(obj)
                            if (invoke == null) {
                                nullValueCount += 1
                            }
                        } catch (e: IllegalAccessException) {
                            e.printStackTrace()
                        } catch (e: InvocationTargetException) {
                            e.printStackTrace()
                        }
                    }
                }
                return methodCount == nullValueCount
            }
        }
        return false
    }

    /**
     * @param o 实体类
     * @return 对象是否为空
     */
    fun objIsNull(o: Any?): Boolean {
        return o == null
    }

    /**
     * @param o 实体类
     * @return 对象是否不为空
     */
    fun objNotNull(o: Any?): Boolean {
        return !objIsNull(o)
    }

    /**
     * @param params 多个判断参数
     * @return 查询参数中是否有Null值或者空白
     */
    fun parametersHaveNull(vararg params: Any): Boolean {
        var b = false
        for (o in params) {
            if (!strNotBlank(o)) {
                b = true
                break
            }
        }
        return b
    }

    /**
     * @param str 字符串
     * @return 字符串是否为数字
     */
    fun strIsNumber(str:String): Boolean {
        val m: Matcher = numPattern.matcher(str)
        return m.matches()
    }

}