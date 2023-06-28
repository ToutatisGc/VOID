package cn.toutatis.xvoid.toolkit.constant

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * 时间相关常量和常用方法
 *
 * <p>提供了日期格式化、获取当前时间、获取当前毫秒数和秒数、正则时间格式化等方法。</p>
 * <p>注意：该类线程安全。</p>
 *
 * @author Toutatis_Gc
 * @since 2023-06-21
 * @version 0.0.0
 */
object Time {

    /**
     * 日期格式正则表达式：yyyy-MM-dd
     * 获取年月日
     */
    const val YYYYMMDD_FORMAT_REGEX = "yyyy-MM-dd"

    /**
     * 日期时间格式正则表达式：yyyy-MM-dd HH:mm:ss
     * 精确到年月日时分秒
     */
    const val DATE_FORMAT_REGEX = "yyyy-MM-dd HH:mm:ss"

    /**
     * 统一目录时间格式：yyyyMMdd
     */
    const val DIR_UNIFORM_TIME = "yyyyMMdd"

    /**
     * 连接时间格式正则表达式：yyyyMMddHHmmss
     */
    const val CONCAT_TIME_REGEX = "yyyyMMddHHmmss"

    @Volatile
    var simpleDateFormat = SimpleDateFormat(DATE_FORMAT_REGEX)

    /**
     * 获取当前时间的格式化字符串
     * @return 格式化的当前时间
     */
    @JvmStatic
    @get:Synchronized
    val currentTime: String get() = simpleDateFormat.format(System.currentTimeMillis())

    /**
     * @return 获取当前毫秒
     */
    @JvmStatic
    val currentMillis: Long get() = System.currentTimeMillis()

    /**
     * @return 获取当前秒
     */
    @JvmStatic
    val currentSeconds: Long get() = currentMillis / 1000

    /**
     * @param time 传入值
     * @return 格式化值
     */
    @JvmStatic
    @Synchronized
    fun getCurrentTimeByLong(time: Long): String = simpleDateFormat.format(Date(time))

    @JvmStatic
    fun getCurrentLocalDateTime(): String = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT_REGEX))

    /**
     * @param format 格式化正则
     * @param date 传入日期
     * @return 格式化时间
     */
    @JvmStatic
    fun regexTime(format: String, date: Date): String = SimpleDateFormat(format).format(date)

    /**
     * @param format 格式化正则
     * @param time 传入日期
     * @return 格式化时间
     */
    @JvmStatic
    fun regexTime(format: String, time: Long): String = SimpleDateFormat(format).format(time)


    /**
     * @param str 字符串转Date
     *
     */
    @JvmStatic
    fun parseData(str :String) : Date{
        return simpleDateFormat.parse(str)
    }

    /**
     * ISSUE 暂留
     * @param pattern 格式化
     * @param time 日期
     * @return 忘了当时的调用逻辑了
     */
    @JvmStatic
    fun isSameDay(pattern: String, time: Long): Boolean = regexTime(pattern, Date()) == regexTime(pattern, time)

}