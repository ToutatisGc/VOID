package cn.toutatis.xvoid.toolkit.constant

/**
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
     * 数字正则
     */
    const val NUMBER_REGEX = "-?[0-9]+(\\.[0-9]+)?"

    /**
     * 图片后缀正则(目前支持这么多)
     * */
    const val IMAGE_SUFFIX_REGEX = "png|jpg|jpeg"

    /**
     * 转换ant格式到regex格式
     */
    private fun parseAntToRegex( antPattern: String):String {
        var regex = antPattern
        regex = regex.replace("*", ".*")
        regex = regex.replace("?", ".")
        regex = regex.replace("**", ".*")
        regex = regex.replace("{", "(")
        regex = regex.replace("}", ")")
        regex = regex.replace("[", "\\[")
        regex = regex.replace("]", "\\]")
        regex = regex.replace("!", "^")
        return regex
    }

}