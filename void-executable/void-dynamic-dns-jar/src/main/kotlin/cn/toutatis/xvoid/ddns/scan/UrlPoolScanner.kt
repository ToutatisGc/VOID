package cn.toutatis.xvoid.ddns.ip.scan

import cn.toutatis.xvoid.ddns.DynamicDNSResolver.Companion.config
import cn.toutatis.xvoid.ddns.constance.ParamConstance
import cn.toutatis.xvoid.toolkit.constant.Regex
import com.alibaba.fastjson.JSONArray
import okhttp3.*
import org.slf4j.LoggerFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * 解析IP工具
 * 如果你想要获取你的网络连接所使用的公网IP地址，你需要向外部的一个服务器发送请求，
 * 服务器能够检测到请求的源IP地址，即你的公网IP地址。
 * 需要这样的服务发送HTTP请求的原因。这个服务能够检测到请求的源IP地址，然后返回给你。
 * url-pool.json文件中有预设的IP地址池，你可以使用这些地址池中的地址来发送请求。
 */
class UrlPoolScanner(private val urlPool:JSONArray) {

    companion object{
        /**
         * Http client HTTP请求客户端
         */
        private lateinit var httpClient : OkHttpClient

        /**
         * Ip Regex IPv4地址正则
         */
        const val IP_REGEX = Regex.IPV4_ADDRESS_REGEX

        /**
         * Logger 日志
         */
        private val logger = LoggerFactory.getLogger(UrlPoolScanner::class.java)
    }

    /**
     * 初始化创建HTTP请求客户端
     */
    init {
        val requestTimeOut = config.getProperty(ParamConstance.REQUEST_TIME_OUT_PARAM).toLong()
        val httpClient: OkHttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            .connectTimeout(requestTimeOut, TimeUnit.SECONDS)
            .readTimeout(requestTimeOut, TimeUnit.SECONDS)
            .build()
        Companion.httpClient = httpClient
    }

    fun getIp(): JSONArray? {
        val analysisIpPool = analysisIpPool()
        return if (analysisIpPool.isNotEmpty()){
            val defaultChooseFirstAddress = config.getProperty("Default-Choose-First-Address").toBoolean()
            if (defaultChooseFirstAddress){
                val headIp = analysisIpPool[0]
                val jsonArray = JSONArray()
                jsonArray.add(headIp)
                jsonArray
            }else{
                JSONArray(analysisIpPool)
            }
        }else{
            null
        }
    }

    private fun analysisIpPool(): List<Map.Entry<String, Int>> {
        val timesHash = HashMap<String, Int>()
        var requestFinishNum = 0
        logger.info("当前解析地址数:${urlPool.size}")
        for (ipAdr in urlPool) {
            val requestBuilder = Request.Builder()
            requestBuilder.url(ipAdr as String)
            val request = requestBuilder.build()
            val call = httpClient.newCall(request)
            call.enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    try {
                        val responseBody = response.body.string()
                        val pattern: Pattern = Pattern.compile(IP_REGEX)
                        val matcher: Matcher = pattern.matcher(responseBody)
                        if (matcher.find()){
                            val group = matcher.group()
                            if (timesHash.containsKey(group)){
                                timesHash[group] = timesHash[group]!!+1
                            }else{
                                timesHash[group] = 1
                            }
                        }
                        requestFinishNum++
                        logger.info("解析成功 [${call.request().url}] RATE:${requestFinishNum}/${urlPool.size} √")
                    }catch (e: SocketTimeoutException){
                        requestFinishNum++
                        logger.warn("解析失败 [${call.request().url}] RATE:${requestFinishNum}/${urlPool.size} ×")
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    requestFinishNum++
                    logger.warn("解析失败 [${call.request().url}] RATE:${requestFinishNum}/${urlPool.size} ×")
                }
            })
        }
        while (true){
            Thread.sleep(100)
            if (requestFinishNum == urlPool.size) {
                break
            }
        }

        val list: List<Map.Entry<String, Int>> = ArrayList<Map.Entry<String, Int>>(timesHash.entries)
        Collections.sort(list) { (_, value0), (_, value1) -> value1.compareTo(value0) }
        return list

    }

}