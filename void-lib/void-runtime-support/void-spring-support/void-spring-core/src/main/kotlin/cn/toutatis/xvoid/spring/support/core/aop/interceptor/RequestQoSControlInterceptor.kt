package cn.toutatis.xvoid.spring.support.core.aop.interceptor

import cn.toutatis.xvoid.common.annotations.qos.RequestQoS
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Toutatis_Gc
 * 请求服务质量控制拦截器
 * 该拦截器控制请求次数等
 */
@Component
class RequestQoSControlInterceptor (voidGlobalConfiguration: VoidGlobalConfiguration) : HandlerInterceptor {

    private val qualityOfServiceStrategyConfig: VoidGlobalConfiguration.QualityOfServiceStrategyConfig = voidGlobalConfiguration.qualityOfServiceStrategyConfig

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (qualityOfServiceStrategyConfig.enableRequestCountLimit){
            if (handler is HandlerMethod){
                var anno: RequestQoS? = handler.getMethodAnnotation(RequestQoS::class.java)
                if (anno == null){
                    val methodClass = handler.beanType
                    anno = methodClass.getAnnotation(RequestQoS::class.java)
                }
                if (anno != null){
                    return true
                }else{
                    return true
                }
            }else{
                // TODO 禁止盗链
                return true
            }
        }else{
            return true
        }
    }
}