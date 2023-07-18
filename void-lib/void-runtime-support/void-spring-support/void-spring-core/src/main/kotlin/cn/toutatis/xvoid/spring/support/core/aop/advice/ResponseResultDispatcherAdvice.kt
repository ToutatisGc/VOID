package cn.toutatis.xvoid.spring.support.core.aop.advice

import cn.toutatis.xvoid.common.standard.StandardFields
import cn.toutatis.xvoid.data.common.result.ProxyResult
import cn.toutatis.xvoid.data.common.result.Result
import cn.toutatis.xvoid.data.common.result.branch.DetailedResult
import cn.toutatis.xvoid.data.common.result.branch.SimpleResult
import cn.toutatis.xvoid.spring.configure.system.VoidConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import javax.servlet.http.HttpServletRequest

/**
 * @author Toutatis_Gc
 * @date 2022/6/8 21:22
 * 返回代理类分发处理
 */
@RestControllerAdvice
class ResponseResultDispatcherAdvice : ResponseBodyAdvice<Any>{

    @Autowired
    private lateinit var request :  HttpServletRequest

    @Autowired
    private lateinit var voidConfiguration : VoidConfiguration

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
        if (data == null) return null
        return if (data::class == ProxyResult::class){
            data as ProxyResult
            var useDetailedMode :Boolean? = data.useDetailedMode
            if (useDetailedMode == null){
                useDetailedMode = voidConfiguration.globalServiceConfig.useDetailedMode
            }
            val result: Result = if(useDetailedMode!!){
                val detailedResult = DetailedResult(data.resultCode,data.message,data.data)
                detailedResult.rid = request.getAttribute(StandardFields.FILTER_REQUEST_ID_KEY) as String?
                if(data.supportMessage != null){
                    if(data.supportMessage.startsWith(ProxyResult.PLACEHOLDER_HEADER)){
                        detailedResult.supportMessage =  detailedResult.supportMessage.replace("{}",data.supportMessage.replace(ProxyResult.PLACEHOLDER_HEADER,""))
                    }else{
                        detailedResult.supportMessage = data.supportMessage
                    }
                }else{
                    detailedResult.supportMessage = data.resultCode.extraInfo.replace("[{}]","")
                }
                detailedResult
            }else{
                val simpleResult = SimpleResult(data.resultCode,data.message,data.data)
                simpleResult.rid = request.getAttribute(StandardFields.FILTER_REQUEST_ID_KEY) as String
                simpleResult.supportMessage = if( data.supportMessage != null) data.supportMessage else null
                simpleResult
            }
            result.serialize()
        }else{
            data
        }
    }
}