package cn.toutatis.xvoid.spring.support.core.aop.advice

import cn.toutatis.xvoid.common.result.ProxyResult
import cn.toutatis.xvoid.common.result.Result
import cn.toutatis.xvoid.common.result.branch.DetailedResult
import cn.toutatis.xvoid.common.result.branch.SimpleResult
import cn.toutatis.xvoid.common.standard.StandardFields
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration
import cn.toutatis.xvoid.toolkit.validator.Validator
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

/**
 * @author Toutatis_Gc
 * @date 2022/6/8 21:22
 * 返回代理类分发处理
 */
@Order(10)
@RestControllerAdvice
class ResponseResultDispatcherAdvice : ResponseBodyAdvice<Any>{

    @Autowired
    private lateinit var request :  HttpServletRequest

    @Autowired
    private lateinit var voidGlobalConfiguration : VoidGlobalConfiguration

    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean = true

    override fun beforeBodyWrite(
        body: Any?, returnType: MethodParameter, selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest, response: ServerHttpResponse
    ): Any? {
        return this.proxyResult(body)
    }

    /**
     * 代理Result派生类
     */
    fun proxyResult(data:Any?): Any? {
        // data为空则返回空
        if (data == null) return null
        // 判断是否为ProxyResult,如果是则处理返回结果
        return if (data::class == ProxyResult::class){
            data as ProxyResult
            // 判断是否为详细模式
            var useDetailedMode :Boolean? = data.useDetailedMode
            if (useDetailedMode == null){
                useDetailedMode = voidGlobalConfiguration.globalServiceConfig.useDetailedMode
            }
            // 对返回信息做处理
            val result: Result = if(useDetailedMode!!){
                val detailedResult = DetailedResult(data.resultCode,data.message,data.data)
                detailedResult.redirectUrl = data.redirectUrl
                detailedResult.rid = request.getAttribute(StandardFields.FILTER_REQUEST_ID_KEY) as String?
                // 如果辅助信息存在则处理
                if(Validator.strNotBlank(data.supportMessage)){
                    if(data.supportMessage.startsWith(ProxyResult.PLACEHOLDER_HEADER)){
                        // detailedResult.supportMessage 的默认值为响应码的extraInfo
                        detailedResult.supportMessage =  detailedResult.supportMessage.replace("{}",data.supportMessage.replace(ProxyResult.PLACEHOLDER_HEADER,""))
                    }else{
                        detailedResult.supportMessage = data.supportMessage
                    }
                // 辅助信息不存则返回响应码的extraInfo并替换模板量
                }else{
                    detailedResult.supportMessage = data.resultCode.extraInfo.replace(ProxyResult.FORMAT_HEADER,"")
                }
                detailedResult
            }else{
                val simpleResult = SimpleResult(data.resultCode,data.message,data.data)
                simpleResult.redirectUrl = data.redirectUrl
                simpleResult.supportMessage = if( data.supportMessage != null) data.supportMessage else data.resultCode.extraInfo.replace(ProxyResult.FORMAT_HEADER,"")
                simpleResult
            }
            result.serialize()
        }else{
            data
        }
    }
}