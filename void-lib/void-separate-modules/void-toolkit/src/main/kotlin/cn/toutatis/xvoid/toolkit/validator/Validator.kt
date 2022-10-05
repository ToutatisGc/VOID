package cn.toutatis.xvoid.toolkit.validator

/**
 * @author Toutatis_Gc
 * @date 2022/10/5 23:28
 * 参数校验器
 */
public object Validator {

    /**
     * @param o 可转String的object
     * @return 返回是否为空
     */
    fun strNotBlank(o: Any?): Boolean {
        if(o == null){ return false }
        val s = o.toString().trim()
        return "" != s && "null" != s
    }

    fun strIsBlank(o: Any): Boolean {
        val s = o.toString() ?: return true
        val trim = s.trim { it <= ' ' }.toLowerCase()
        return trim.length == 0 || "null" == trim || "undefined" == trim
    }

    /**
     * 此方法会去除左右空格
     * @param o 可转String的object
     * @return 返回是否为空
     */
    fun strNotBlankTrim(o: Any?): Boolean {
        val s = o.toString()
        return o != null && "" != s.trim { it <= ' ' }
    }

    /**
     * @param o 实体类
     * @return 对象是否为空
     */
    fun objectIsNull(o: Any?): Boolean {
        return o == null
    }

    /**
     * @param o 实体类
     * @return 对象是否为空
     */
    fun objectNotNull(o: Any?): Boolean {
        return !objectIsNull(o)
    }

    /**
     * @param parameter 判断参数
     * @return 查询参数中是否有Null值或者空白
     */
    fun parametersHaveNull(vararg parameter: Any): Boolean {
        var b = false
        for (o in parameter) {
            if (!strNotBlank(o)) {
                b = true
                break
            }
        }
        return b
    }

}