package cn.toutatis.xvoid.constant

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
}