package cn.toutatis.ip.scan

import cn.toutatis.xvoid.resolve.ip.IPResolver.Companion.config
import com.alibaba.fastjson.JSONArray
import okhttp3.*
import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern


class UrlPoolScanner constructor(private val urlPool:JSONArray) {

    companion object{

        private lateinit var httpClient : OkHttpClient

        const val IP_REGEX = "((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}"

        private val logger = LoggerFactory.getLogger(UrlPoolScanner::class.java)

    }

    init {
        val requestTimeOut = config.getProperty("Request-Time-Out").toLong()
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
                    val responseBody = response.body!!.string()
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