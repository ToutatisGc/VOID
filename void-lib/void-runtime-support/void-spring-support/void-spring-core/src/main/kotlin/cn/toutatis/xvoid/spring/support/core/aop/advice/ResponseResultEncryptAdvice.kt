package cn.toutatis.xvoid.spring.support.core.aop.advice

import cn.toutatis.xvoid.common.result.AbstractResult
import cn.toutatis.xvoid.common.result.ResultCode
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration
import cn.toutatis.xvoid.spring.configure.system.VoidSecurityConfiguration
import com.alibaba.fastjson.JSON
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import jakarta.servlet.http.HttpServletRequest

/**
 * @author Toutatis_Gc
 * @date 2022/6/8 21:22
 * 返回代理类分发处理
 */
@Order(100)
@RestControllerAdvice
class ResponseResultEncryptAdvice : ResponseBodyAdvice<Any>{

    @Autowired
    private lateinit var request :  HttpServletRequest

    @Autowired
    private lateinit var voidGlobalConfiguration : VoidGlobalConfiguration

    @Autowired
    private lateinit var voidSecurityConfiguration: VoidSecurityConfiguration

    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean = true

    override fun beforeBodyWrite(
        body: Any?, returnType: MethodParameter, selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest, response: ServerHttpResponse
    ): Any? {
//        returnType.getMethodAnnotation()
        return body
    }

    fun encryptData(body: Any?,ignore:Boolean):Any?{
        if (body == null || ignore || body !is AbstractResult){ return body }
        if (!voidGlobalConfiguration.isDebugging){
            if (voidSecurityConfiguration.dataConfig.responseDataEncrypt){
                if (body.data != null){
                    try {
                        val data = JSON.toJSONString(body.data)
//                        DigestToolkit.aesUseCBCWithBase64()
                        body.data = data
                    }catch (e:Exception){
                        e.printStackTrace()
                        body.setResultCode(ResultCode.INNER_EXCEPTION)
                        body.data = null
                    }
                }
            }
        }
        return body
    }

    private fun encryptData():Any?{
        return null;
    }


}