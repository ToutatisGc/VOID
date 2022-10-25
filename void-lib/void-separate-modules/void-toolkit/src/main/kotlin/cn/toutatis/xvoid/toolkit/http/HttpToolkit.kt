package cn.toutatis.xvoid.toolkit.http

import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.Logger
import java.io.IOException
import java.util.*


/**
 * @author Toutatis_Gc
 * @date 2022/10/5 23:20
 * 发送请求工具类
 */
object HttpToolkit {

    private val logger: Logger = LoggerToolkit.getLogger(HttpToolkit::class.java)

    private var httpClient:OkHttpClient

    init {
        /*TODO 获取环境配置*/
        httpClient = OkHttpClient()
    }

    /**
     * 同步GET请求
     */
    @JvmStatic
    fun syncGet(url:String,params:Map<String,String>? = null ,headers:Map<String,String>? = null):String?{
        val builder: Request.Builder = Request.Builder()
        val urlWithParams = concatMapParameters(url, params)
        builder.url(urlWithParams)
        this.addHeader(builder,headers)
        val request: Request = builder.build()
        val responseBody = getResponseBody(request)
        System.err.println(responseBody)
        return  responseBody
    }

    fun asyncGet(){

    }


    /**
     * @param url post请求地址
     * @param parameters 参数
     * @return 请求响应体
     */
    fun post(url: String, parameters: Map<String,String>? = null,headers:Map<String,String>? = null): String? {
        val builder = FormBody.Builder()
        if (parameters != null && parameters.isNotEmpty()) {
            for (key in parameters.keys) {
                parameters[key]?.let { builder.add(key, it) }
            }
        }
        val requestBuilder = Request.Builder()
        this.addHeader(requestBuilder,headers)
        val request: Request = requestBuilder
                .url(url)
                .post(builder.build())
                .build()
        return getResponseBody(request)
    }

    /**
     * @param url get地址
     * @param queries get参数
     * @return 拼接get的请求地址和参数
     */
    private fun concatMapParameters(url: String, queries: Map<String, String>?): String {
        val stringBuilder = StringBuilder(url)
        if (queries != null && queries.isNotEmpty()) {
            var firstParameterFlag = true
            for ((key, value) in queries) {
                if (firstParameterFlag) {
                    stringBuilder.append("?$key=$value")
                    firstParameterFlag = false
                } else {
                    stringBuilder.append("&$key=$value")
                }
            }
        }
        return stringBuilder.toString()
    }

    /**
     * @param request 请求
     * @return 获取请求内容
     */
    private fun getResponseBody(request: Request): String? {
        var message: String? = null
        try {
            httpClient.newCall(request).execute().use { response ->
                if (response.isSuccessful) message = Objects.requireNonNull(response.body).string()
            }
        } catch (e: IOException) {
            logger.error("对外请求失败,请查询原因[URL:" + request.url.toString() + "]")
        }
        return message
    }

    /**
     * 填充头部
     */
    private fun addHeader(request: Request.Builder,headers:Map<String,String>?){
        headers?.forEach { (k, v) -> request.addHeader(k, v) }
    }

}