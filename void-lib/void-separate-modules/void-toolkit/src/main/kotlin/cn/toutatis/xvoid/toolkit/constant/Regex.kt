package cn.toutatis.xvoid.toolkit.constant

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

    const val SIMPLE_USERNAME_REGEX = "^[a-zA-Z0-9_\u4e00-\u9fa5]{2,32}\$"

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

}