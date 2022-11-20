package cn.toutatis.xvoid.support.spring.core.aop.advice

import cn.toutatis.xvoid.data.common.result.ProxyResult
import cn.toutatis.xvoid.data.common.result.Result
import cn.toutatis.xvoid.data.common.result.branch.DetailedResult
import cn.toutatis.xvoid.data.common.result.branch.SimpleResult
import org.springframework.core.MethodParameter
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
@RestControllerAdvice
class ResponseResultDispatcherAdvice : ResponseBodyAdvice<Any>{

    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean = true

    override fun beforeBodyWrite(
        body: Any?, returnType: MethodParameter, selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest, response: ServerHttpResponse
    ): Any? {
        return this.proxyResult(body)
    }

    fun proxyResult(data:Any?): Any? {
        if (data == null) return null
        return if (data::class == ProxyResult::class.java){
            data as ProxyResult
            val result: Result = (if (data.useDetailedMode){ DetailedResult() } else { SimpleResult() })
                .apply {
                    setResultCode(data.resultCode)
                    setData(data.data)
            }
            result
        }else{
            data;
        }
    }
}