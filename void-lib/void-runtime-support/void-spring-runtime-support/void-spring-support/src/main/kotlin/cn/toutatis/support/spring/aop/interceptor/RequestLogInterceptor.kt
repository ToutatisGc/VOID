package cn.toutatis.support.spring.aop.interceptor

import cn.toutatis.constant.Time
import cn.toutatis.toolkit.log.LoggerToolkit
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class RequestLogInterceptor : HandlerInterceptor {

    private val logger = LoggerToolkit.getLogger(RequestLogInterceptor::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod) {
            val beanType: Class<*> = handler.beanType
            logger.info("<======REQUEST-TIME:${Time.getCurrentLocalDateTime()}=========================>")
            logger.info("请求类:${beanType.name},请求方法:${handler.method.name}")
            logger.info("请求地址：" + request.requestURL.toString() + "\t请求类型：" + request.method)
        }
        return true
    }

}