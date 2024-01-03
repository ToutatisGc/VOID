package cn.toutatis.xvoid.toolkit.constant

import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException




/**
 * Regex 类是一个 Kotlin 对象（object），用于存储常用的正则表达式模式。
 * 它包含了一些静态的常量和一些私有函数，提供了方便的正则表达式操作。
 * @author Toutatis_Gc
 * @since 2020-10-01
 * @version 0
 * 常用正则
 */
object Regex {
    /**
     * 手机号正则
     */
    const val TEL_REGEX = "^(1[3-9]\\d{9}$)"

    /**
     * 邮箱正则
     */
    const val EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$"

    /**
     * 标点符号正则
     */
    const val PUNCTUATION_REGEX = "[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]"

    /**
     * 普通中文用户名正则
     * 包含下划线,大小写英文,数字
     * 限制长度2-32位
     */
    const val SIMPLE_USERNAME_REGEX = "^[a-zA-Z0-9_\u4e00-\u9fa5]{2,32}\$"

    /**
     * 纯英文用户名正则
     * 大小写英文,数字,下划线
     * 限制长度2-32位
     */
    const val EN_USERNAME_REGEX = "^[a-zA-Z0-9_]{2,32}\$"

    /**
     * 数字正则
     */
    const val NUMBER_REGEX = "-?[0-9]+(\\.[0-9]+)?"

    /**
     * 图片后缀正则(目前支持这么多)
     * */
    const val IMAGE_SUFFIX_REGEX = "png|jpg|jpeg"

    /**
     * Request JSession Id Regex
     */
    const val REQUEST_JSESSION_ID_REGEX  = "JSESSIONID=([a-fA-F0-9]+);"

    /**
     * Password Regex 01
     * 密码至少包含 8 个字符，包括至少一个大写字母、一个小写字母和一个数字
     */
    const val PASSWORD_REGEX_01 = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,32}\$"

    /**
     * Password Regex 02
     * 密码至少包含 8 个字符，可以是字母、数字或特殊字符，但不允许有空格
     */
    const val PASSWORD_REGEX_02 = "^(?!.*\\s)[A-Za-z\\d@#\$%^&+=]{8,32}\$"

    /**
     * Password Regex 03
     * 密码的长度在8到32位之间，只包含英文字母、数字以及常见的符号
     */
    const val PASSWORD_REGEX_03 = "^[A-Za-z0-9!@#\\\\\$%\\\\^&*()-_+=<>?]{8,32}\$"

    /**
     * Password Regex 04
     * 密码的长度在8到32位之间，包含至少一位英文字母，以及只包含英文字母、数字和常见的符号
     */
    const val PASSWORD_REGEX_04 = "^(?=.*[A-Za-z])[A-Za-z0-9!@#\\\\\$%\\\\^&*()-_+=<>?]{8,32}\$"

    /**
     * 该函数接受一个 Ant 格式的URL匹配模式作为参数，并返回对应的正则表达式
     * @param antPattern ant pattern 语法
     * @return 由ant格式通配表达式转化为正则表达式
     */
    private fun parseAntToRegex(antPattern: String):String {
        var regex = antPattern
        regex = regex.replace("*", ".*")
        regex = regex.replace("?", ".")
        regex = regex.replace("**", ".*")
        regex = regex.replace("{", "(")
        regex = regex.replace("}", ")")
        regex = regex.replace("[", "[")
        regex = regex.replace("]", "]")
        regex = regex.replace("!", "^")
        return regex
    }

    /**
     * Split alpha numeric
     * 分割字母和数字
     * @param input 字符串
     * @return index：0为字母，1为数字
     */
    @JvmStatic
    public fun splitAlphaNumeric(input: String): Array<String> {
        // 使用正则表达式匹配字母和数字部分
        val pattern = Pattern.compile("([a-zA-Z]+)(\\d+)")
        val matcher: Matcher = pattern.matcher(input)
        // 如果匹配成功，则获取字母和数字部分
        return if (matcher.matches()) {
            val alphaPart: String = matcher.group(1)
            val numericPart: String = matcher.group(2)
            arrayOf(alphaPart, numericPart)
        } else {
            // 如果不匹配，返回空数组
            arrayOf()
        }
    }

    /**
     * Convert single line
     * 转换字符串为单行
     * @param input 输入字符串
     * @return 单行字符串
     */
    @JvmStatic
    public fun convertSingleLine(input: String):String{
        return input.replace("\\s".toRegex(), "")
    }

    /**
     * Validate regex
     * 验证正则表达式是否合法
     * @param regex 正则表达式
     * @return 表达式是否合法
     */
    fun validateRegex(regex: String?): Boolean {
        if (regex == null) { return false }
        return try {
            // 尝试编译正则表达式
            Pattern.compile(regex)
            // 编译成功，说明是合法的正则表达式
            true
        } catch (e: PatternSyntaxException) {
            // 编译失败，说明不是合法的正则表达式
            false
        }
    }


}