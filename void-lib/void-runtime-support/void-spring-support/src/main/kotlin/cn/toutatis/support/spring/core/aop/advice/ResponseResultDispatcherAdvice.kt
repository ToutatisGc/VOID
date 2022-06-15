package cn.toutatis.support.spring.core.aop.advice

import cn.toutatis.data.branch.DetailedResult
import cn.toutatis.data.branch.SimpleResult
import cn.toutatis.data.common.ProxyResult
import cn.toutatis.data.implement.Result
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

    override fun beforeBodyWrite(body: Any?, returnType: MethodParameter, selectedContentType: MediaType,
                                 selectedConverterType: Class<out HttpMessageConverter<*>>,
                                 request: ServerHttpRequest,
                                 response: ServerHttpResponse
    ): Any? {
        if (body == null) return null
        if (body::class == ProxyResult::class.java){
            body as ProxyResult
            val result: Result = if(body.isUseSimpleMode) SimpleResult() else DetailedResult()
            result.setResultCode(body.resultCode)
            return result
        }
        return body
    }
}