package cn.toutatis.xvoid.spring.support.core.aop.advice

import cn.toutatis.xvoid.common.standard.StandardFields
import cn.toutatis.xvoid.common.result.ProxyResult
import cn.toutatis.xvoid.common.result.Result
import cn.toutatis.xvoid.common.result.branch.DetailedResult
import cn.toutatis.xvoid.common.result.branch.SimpleResult
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.core.annotation.Order
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
@Order(2)
@RestControllerAdvice
class ResponseResultEncryptAdvice : ResponseBodyAdvice<Any>{

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
        System.err.println("加密")
        return body
    }


}