package cn.toutatis.xvoid.toolkit.validator

import cn.toutatis.xvoid.toolkit.Meta
import cn.toutatis.xvoid.toolkit.constant.Regex
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.errorWithModule
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

    private val CN_USERNAME_PATTERN = Pattern.compile(Regex.SIMPLE_USERNAME_REGEX)

    private val EN_USERNAME_PATTERN = Pattern.compile(Regex.EN_USERNAME_REGEX)

    private val USEFUL_PASSWORD_PATTERN = Pattern.compile(Regex.PASSWORD_REGEX_04)

    private const val SUB_MODULE_NAME = "VALIDATOR"

//    private
    /**
     * @param o 可转String的object
     * @return 返回自然情况下是否为空
     */
    @JvmStatic
    fun strNotBlank(o: Any?): Boolean {
        if(o == null){ return false }
        val s = o.toString().trim()
        return s.isNotEmpty() && "" != s && "null" != s.lowercase()
    }

    /**
     * @param o 可转String的object
     * @return 返回是否是空
     */
    @JvmStatic
    fun strIsBlank(o: Any?): Boolean {
        if (o == null) return true
        val s = o.toString() ?: return true
        val trim = s.trim { it <= ' ' }.lowercase()
        return trim.isEmpty() || "null" == trim || "undefined" == trim
    }

    /**
     * 对象字段为null
     * @param obj 实体类
     * @return 字段是否为空
     */
    @JvmStatic
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
    @JvmStatic
    fun objIsNull(o: Any?): Boolean {
        if (o == null) return true
        return if (o is Map<*,*> && o.isEmpty()) true
        else o is List<*> && o.isEmpty()
    }

    /**
     * @param o 实体类
     * @return 对象是否不为空
     */
    @JvmStatic
    fun objNotNull(o: Any?): Boolean {
        return !objIsNull(o)
    }

    /**
     * @param params 多个判断参数
     * @return 查询参数中是否有Null值或者空白
     */
    @JvmStatic
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

    @JvmStatic
    fun stringsHasNull(vararg o: String): Boolean {
        for (s in o) {
            if (strIsBlank(s)){
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun stringsNotNull(vararg o: String): Boolean{
        for (s in o) {
            if (strIsBlank(s)){
                return false
            }
        }
        return true
    }

    /**
     * @param str 字符串
     * @return 字符串是否为数字
     */
    @JvmStatic
    fun strIsNumber(str:String): Boolean {
        val m: Matcher = numPattern.matcher(str)
        return m.matches()
    }


    /**
     * Check cn username
     * 检查用户名
     * @param username 用户名
     * @return 是否符合正则
     */
    @JvmStatic
    fun checkCNUsernameFormat(username:String): Boolean {
        val m: Matcher = CN_USERNAME_PATTERN.matcher(username)
        return m.matches()
    }

    /**
     * Check useful password format
     * 检查密码是否匹配要求格式
     * @param password 输入密码
     * @return 是否匹配
     */
    @JvmStatic
    fun checkUsefulPasswordFormat(password:String): Boolean {
        val m: Matcher = USEFUL_PASSWORD_PATTERN.matcher(password)
        return m.matches()
    }

    /**
     * Check map contains key
     * 检查Map中是否存在该键,不存在则返回缺失字段的列表
     * Check whether the key exists in the Map. If no, return a list of missing fields.
     * @param map 列表
     * @param keys 键名
     * @return 缺失字段列表
     */
    @JvmStatic
    fun checkMapContainsKey(map:Map<String,Any>,vararg keys:String):List<String>{
        val keyList = mutableListOf<String>()
        for (key in keys) {
            if (!map.containsKey(key)) keyList.add(key)
        }
        return keyList
    }

    @JvmStatic
    fun checkMapContainsKeyBoolean(map:Map<String,Any>,vararg keys:String):Boolean{
        val checkMapContainsKey = this.checkMapContainsKey(map, *keys);
        if (checkMapContainsKey.isNotEmpty()) return false
        return true
    }

    @JvmStatic
    fun checkMapContainsKeyThrowEx(map:Map<String,Any>,vararg keys:String){
        val checkMapContainsKey = this.checkMapContainsKey(map, *keys);
        if (checkMapContainsKey.isNotEmpty()){
            val key = checkMapContainsKey[0]
            val errorLog = logger.errorWithModule(Meta.MODULE_NAME, SUB_MODULE_NAME, "缺失属性${key}")
            throw IllegalArgumentException(errorLog)
        }
    }

}