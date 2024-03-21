package cn.toutatis.xvoid.toolkit.http

import cn.toutatis.xvoid.common.exception.base.UnSettledException
import cn.toutatis.xvoid.common.exception.base.VoidIOException
import cn.toutatis.xvoid.common.exception.base.VoidRuntimeException
import cn.toutatis.xvoid.common.exception.parameter.WrongParameterException
import cn.toutatis.xvoid.toolkit.Meta
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.errorWithModule
import cn.toutatis.xvoid.toolkit.log.warnWithModule
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.slf4j.Logger
import java.io.IOException
import java.net.UnknownHostException


/**
 * @author Toutatis_Gc
 * @date 2022/10/5 23:20
 * 发送请求工具类
 */
object HttpToolkit {

    private val logger: Logger = LoggerToolkit.getLogger(HttpToolkit::class.java)

    private var httpClient:OkHttpClient

    init {
        httpClient = OkHttpClient()
    }

    @JvmStatic
    fun syncGet(url:String):String?{
        return syncGet(url,null,null,false)
    }

    @JvmStatic
    fun syncGet(url:String,params:Map<String,String>?):String?{
        return syncGet(url,params,null,false)
    }

    /**
     * 同步GET请求
     */
    @JvmStatic
    fun syncGet(url:String,params:Map<String,String>? = null ,headers:Map<String,String>? = null):String?{
        return syncGet(url,params,headers,false)
    }
    @JvmStatic
    fun syncGet(url:String,params:Map<String,String>? = null ,headers:Map<String,String>? = null,throwException: Boolean):String?{
        return getResponseBodyAsString(buildSimpleRequest(url,params,headers),throwException)
    }

    @JvmStatic
    fun syncGetAsBytes(url:String,params:Map<String,String>? = null ,headers:Map<String,String>? = null,throwException: Boolean):ByteArray?{
        return getResponseBodyAsBytes(buildSimpleRequest(url,params,headers),throwException)
    }

    @JvmStatic
    fun syncGetAsResponse(url:String):Response?{
        return getResponse(buildSimpleRequest(url,null,null),false)
    }

    @JvmStatic
    fun syncGetAsResponse(url:String,params:Map<String,String>? = null):Response?{
        return getResponse(buildSimpleRequest(url,params,null),false)
    }

    @JvmStatic
    fun syncGetAsResponse(url:String,params:Map<String,String>?,headers:Map<String,String>?):Response?{
        return getResponse(buildSimpleRequest(url,params,headers),false)
    }

    @JvmStatic
    fun syncGetAsResponse(url:String,params:Map<String,String>? = null ,headers:Map<String,String>? = null,throwException: Boolean = false):Response?{
        return getResponse(buildSimpleRequest(url,params,headers),throwException)
    }

    /**
     * 构建一般的GET请求
     * @param url 请求地址
     * @param params 请求参数
     * @param headers 请求头
     * @return 请求体
     */
    private fun buildSimpleRequest(url:String, params:Map<String,String>? = null, headers:Map<String,String>? = null): Request {
        val builder: Request.Builder = Request.Builder()
        val urlWithParams = concatMapParameters(url, params)
        builder.url(urlWithParams)
        this.addHeader(builder,headers)
        return builder.build()
    }
    /**
     * @param url post请求地址
     * @param formBody 参数
     * @return 请求响应体
     */
    @JvmStatic
    fun post(url: String, formBody: Map<String,String>? = null, headers:Map<String,String>? = null, throwException: Boolean = false): String? {
        val builder = FormBody.Builder()
        if (!formBody.isNullOrEmpty()) {
            for (key in formBody.keys) {
                formBody[key]?.let { builder.add(key, it) }
            }
        }
        val requestBuilder = Request.Builder()
        this.addHeader(requestBuilder,headers)
        val request: Request = requestBuilder
                .url(url)
                .post(builder.build())
                .build()
        return getResponseBodyAsString(request,throwException)
    }

    /**
     * @param url URL地址
     * @param queries query参数
     * @return 拼接URL请求地址和query参数为一条地址
     */
    @JvmStatic
    fun concatMapParameters(url: String, queries: Map<String, String>?): String {
        val urlBuilder = StringBuilder(url)
        if (!queries.isNullOrEmpty()) {
            var firstParameterFlag = true
            for ((key, value) in queries) {
                if (firstParameterFlag) {
                    urlBuilder.append("?$key=$value")
                    firstParameterFlag = false
                } else {
                    urlBuilder.append("&$key=$value")
                }
            }
        }
        return urlBuilder.toString()
    }

    /**
     * Get response
     * 获取响应
     * @param request 请求
     * @param throwException 是否抛出异常
     * @return 响应实体
     */
    @JvmStatic
    fun getResponse(request: Request,throwException: Boolean): Response? {
        try {
            return httpClient.newCall(request).execute()
        } catch (e: Exception) {
            logger.warnWithModule(Meta.MODULE_NAME, "请求失败[URL:${request.url}]")
            if (!throwException){ return null }
            var exception : VoidRuntimeException? = null
            val errorMessage = e.message
            var returnMessage = e.message
            when(e::class){
                UnknownHostException::class -> {
                    exception = VoidIOException("请求地址[${request.url}]无法解析")
                }
                IOException::class -> {
                    exception = VoidIOException(e.message)
                }
                IllegalArgumentException::class ->{
                    if (errorMessage!=null){
                        if (errorMessage.contains("Invalid URL host")){
                            returnMessage = "错误的请求地址[${request.url}]"
                            exception = WrongParameterException(returnMessage)
                        }
                    }
                }
            }
            if (exception == null){
                e.printStackTrace()
                logger.errorWithModule(Meta.MODULE_NAME,"未处理的错误原因:[${returnMessage}]")
                exception = UnSettledException()
            }else{
                logger.errorWithModule(Meta.MODULE_NAME,"错误原因:[${returnMessage}]")
            }
            throw exception
        }
    }

    /**
     * @param request 请求
     * @return 获取响应体内容
     */
    private fun getResponseBodyAsString(request: Request, throwException: Boolean): String? {
        var message: String?
        val response = getResponse(request, throwException)
        if (response == null){
            if (throwException){
                throw VoidIOException("获取响应体失败,请查询原因[URL:" + request.url.toString() + "]")
            }
            return null
        }
        response.use {
            message = response.body.string()
        }
        return message
    }

    /**
     * @param request 请求
     * @return 获取响应体内容
     */
    private fun getResponseBodyAsBytes(request: Request, throwException: Boolean): ByteArray? {
        var message: ByteArray?
        val response = getResponse(request, throwException)
        if (response == null){
            if (throwException){
                throw VoidIOException("获取响应体失败,请查询原因[URL:" + request.url.toString() + "]")
            }
            return null
        }
        response.use {
            message = response.body.bytes()
        }
        return message
    }

    /**
     * 填充请求头部信息
     * @param request 请求
     * @param headers 请求头部信息
     */
    private fun addHeader(request: Request.Builder,headers:Map<String,String>?){
        headers?.forEach { (k, v) -> request.addHeader(k, v) }
    }

}