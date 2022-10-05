package cn.toutatis.xvoid.support.spring.core.aop.interceptor

import cn.toutatis.xvoid.common.standard.StandardFields
import cn.toutatis.xvoid.support.PkgInfo.MODULE_NAME
import cn.toutatis.xvoid.support.spring.config.VoidConfiguration
import cn.toutatis.xvoid.toolkit.constant.Time
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * 请求日志生成器
 */
@Component
class RequestLogInterceptor(voidConfiguration: VoidConfiguration) : HandlerInterceptor {

    private val logger = LoggerToolkit.getLogger(RequestLogInterceptor::class.java)

    private var recordRequestParams : Boolean = false

    init {
        recordRequestParams = voidConfiguration.globalLogConfig.recordRequestParams
        logger.info("[${MODULE_NAME}] RequestLogInterceptor init success.")
    }

    /**
     * 请求前记录日志
     */
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod) {
            val beanType: Class<*> = handler.beanType
            logger.info("<====REQUEST-TIME:[${Time.getCurrentLocalDateTime()}]======" +
                    "RID:[${request.getAttribute(StandardFields.FILTER_REQUEST_ID)}]=====>")
            logger.info("请求类:${beanType.name}#${handler.method.name}")
            logger.info("请求地址：${request.requestURL}\t请求类型：${request.method}")
            logger.info("MIME-TYPE：${request.contentType?:"-UNKNOWN-"}\tUser-Agent：${request.getHeader("User-Agent")}")
            if (recordRequestParams) {
                var idx = 0;
                request.parameterMap.forEach { (key, value) ->
                    logger.info("PARAM[${idx++}]：$key = ${value.joinToString(",")}")
                }
            }
        }
        return true
    }

}