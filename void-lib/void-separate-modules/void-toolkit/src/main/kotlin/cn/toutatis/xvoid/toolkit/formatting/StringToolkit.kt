package cn.toutatis.xvoid.toolkit.formatting

import cn.toutatis.xvoid.toolkit.validator.Validator
import java.lang.StringBuilder

/**
 * String toolkit
 * 字符串工具类
 * @constructor Create empty String toolkit
 * @author Toutatis_Gc
 */
object StringToolkit {


    /**
     * Line wrap
     * 字符串自动换行
     *
     * 字符串每达到相应length参数则换行一次
     * @param str 字符串
     * @param length 换行长度
     * @return 换行字符串
     */
    @JvmStatic
    fun lineWrap(str:String,length:Int): String {
        return this.lineWrap("",0,str,length)
    }

    /**
     * Line wrap
     * 字符串自动换行
     *
     * 字符串每达到相应length参数则换行一次
     * @param suffix 自定义前缀
     * @param appendTabCount 追加制表符数量
     * @param str 字符串
     * @param length 换行长度
     * @return 换行字符串
     */
    @JvmStatic
    fun lineWrap(suffix:String,appendTabCount:Int,str:String,length:Int): String {
        if (Validator.strIsBlank(str)){ return str }
        if (str.length < length){ return str }
        val separator = System.getProperty("line.separator")
        val times = (str.length / length).toInt()
        val stringBuilder = StringBuilder("")
        for (i in 0 until times){
            if (i != 0){
                stringBuilder.append(suffix)
            }
            if (i != 0 && appendTabCount > 0){
                for (t in 0 until appendTabCount){
                    stringBuilder.append("\t")
                }
            }
            stringBuilder.append(str.substring(i * length, (i + 1) * length)).append(separator)
        }
        val endRange = times * length
        stringBuilder.append(suffix)
        if (appendTabCount > 0){
            for (t in 0 until appendTabCount){
                stringBuilder.append("\t")
            }
        }
        stringBuilder.append(str.substring(endRange, str.length))
        return stringBuilder.toString()
    }

}
