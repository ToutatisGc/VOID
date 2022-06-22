package cn.toutatis.constant

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * @author Toutatis_Gc
 * 关于日期使用的常量和一些常用方法
 */
object Time {

    const val YYYYMMDD_FORMAT_REGEX = "yyyy-MM-dd"
    const val DATE_FORMAT_REGEX = "yyyy-MM-dd HH:mm:ss"
    const val DIR_UNIFORM_TIME = "yyyyMMdd"
    const val CONCAT_TIME_REGEX = "yyyyMMddHHmmss"

    @Volatile
    var simpleDateFormat = SimpleDateFormat(DATE_FORMAT_REGEX)

    /**
     * @return  格式化当前时间
     */
    @get:Synchronized
    val currentTime: String get() = simpleDateFormat.format(System.currentTimeMillis())

    /**
     * @return 获取当前毫秒
     */
    val currentMillis: Long get() = System.currentTimeMillis()

    /**
     * @return 获取当前秒
     */
    val currentSeconds: Long get() = currentMillis / 1000

    /**
     * @param time 传入值
     * @return 格式化值
     */
    @Synchronized
    fun getCurrentTimeByLong(time: Long): String = simpleDateFormat.format(Date(time))

    fun getCurrentLocalDateTime(): String = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT_REGEX))

    /**
     * @param format 格式化正则
     * @param date 传入日期
     * @return 格式化时间
     */
    fun regexTime(format: String, date: Date): String = SimpleDateFormat(format).format(date)

    /**
     * @param format 格式化正则
     * @param time 传入日期
     * @return 格式化时间
     */
    fun regexTime(format: String, time: Long): String = SimpleDateFormat(format).format(time)


    /**
     * @param str 字符串转Date
     *
     */
    fun parseData(str :String) : Date{
        return simpleDateFormat.parse(str)
    }

    /**
     * ISSUE 暂留
     * @param pattern 格式化
     * @param time 日期
     * @return 忘了当时的调用逻辑了
     */
    fun isSameDay(pattern: String, time: Long): Boolean = regexTime(pattern, Date()) == regexTime(pattern, time)

}