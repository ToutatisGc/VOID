package cn.toutatis.xvoid.toolkit.constant

import java.text.SimpleDateFormat
import java.time.*
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
     * 横杠日期格式正则表达式
     * 获取年月日
     */
    const val YMD_HORIZONTAL_FORMAT_REGEX = "yyyy-MM-dd"

    /**
     * YMD Slash Format Regex
     * 斜杠日期正则表达式
     */
    const val YMD_SLASH_FORMAT_REGEX = "yyyy/MM/dd"

    /**
     * 统一目录时间格式：yyyyMMdd
     */
    const val YMD_NON_SEPARATOR_FORMAT_REGEX = "yyyyMMdd"

    /**
     * 连接时间格式正则表达式：yyyyMMddHHmmss
     */
    const val YMD_HMS_NON_SEPARATOR_TIME_REGEX = "yyyyMMddHHmmss"

    /**
     * Hms Colon Format Regex 时分秒正则表达式
     */
    const val HMS_COLON_FORMAT_REGEX = "HH:mm:ss"

    /**
     * 常用日期时间格式正则表达式：yyyy-MM-dd HH:mm:ss
     * 精确到年月日时分秒
     */
    const val SIMPLE_DATE_FORMAT_REGEX = "yyyy-MM-dd HH:mm:ss"

    @Volatile
    var simpleDateFormat = SimpleDateFormat(SIMPLE_DATE_FORMAT_REGEX)

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
    fun getCurrentLocalDateTime(): String = LocalDateTime.now().format(DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT_REGEX))

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
     * Get current day last mills time 获取这一天的最后一秒
     * 即当天最后时间
     * @return 当天最后时间最后一秒
     */
    @JvmStatic
    fun getCurrentDayLastMillsTime(): LocalDateTime {
        val date = LocalDate.now() // 当前日期
        val lastTime = LocalTime.MAX // 最大时间，即23:59:59
        return LocalDateTime.of(date, lastTime)
    }

    /**
     * @param str 字符串转Date
     *
     */
    @JvmStatic
    fun parseData(str :String) : Date{
        return simpleDateFormat.parse(str)
    }

    @JvmStatic
    fun parseTimeToMills(time: LocalDateTime):Long{
        return time.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    /**
     * ISSUE 暂留
     * @param pattern 格式化
     * @param time 日期
     * @return 忘了当时的调用逻辑了
     */
    @JvmStatic
    fun isSameDay(pattern: String, time: Long): Boolean = regexTime(pattern, Date()) == regexTime(pattern, time)

    /**
     * Is before now
     * 参数时间是否在当前时间之前
     * @param time 时间
     * @return 参数时间是否在当前时间之前
     */
    @JvmStatic
    fun isBeforeNow(time:LocalDateTime):Boolean = LocalDateTime.now().isBefore(time)

}