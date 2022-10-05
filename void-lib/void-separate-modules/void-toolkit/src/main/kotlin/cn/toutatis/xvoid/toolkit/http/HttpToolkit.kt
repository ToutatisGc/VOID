package cn.toutatis.xvoid.toolkit.http

import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import org.slf4j.Logger


/**
 * @author Toutatis_Gc
 * @date 2022/10/5 23:20
 * 发送请求工具类
 */
object HttpToolkit {

    private val logger: Logger = LoggerToolkit.getLogger(HttpToolkit::class.java)


    /**
     * @param url get地址
     * @param queries get参数
     * @return 拼接get的请求地址和参数
     */
    fun concatMapParameters(url: String?, queries: Map<String, String>?): String {
        val stringBuilder = StringBuilder(url)
        if (queries != null && queries.keys.size > 0) {
            var firstFlag = true
            for ((key, value) in queries) {
                if (firstFlag) {
                    stringBuilder.append("?$key=$value")
                    firstFlag = false
                } else {
                    stringBuilder.append("&$key=$value")
                }
            }
        }
        return stringBuilder.toString()
    }

}