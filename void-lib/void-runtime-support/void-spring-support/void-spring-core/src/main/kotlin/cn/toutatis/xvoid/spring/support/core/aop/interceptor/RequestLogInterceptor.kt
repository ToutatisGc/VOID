package cn.toutatis.xvoid.spring.support.core.aop.interceptor

import cn.toutatis.xvoid.common.standard.StandardFields
import cn.toutatis.xvoid.spring.support.Meta.MODULE_NAME
import cn.toutatis.xvoid.spring.support.amqp.AmqpShell
import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog
import cn.toutatis.xvoid.orm.base.infrastructure.enums.LogType
import cn.toutatis.xvoid.spring.configure.system.VoidConfiguration
import cn.toutatis.xvoid.toolkit.constant.Time
import cn.toutatis.xvoid.toolkit.http.RequestToolkit
import cn.toutatis.xvoid.toolkit.locale.I18n
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import com.alibaba.fastjson.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * 请求日志生成器
 */
@Component
class RequestLogInterceptor(voidConfiguration: VoidConfiguration) : HandlerInterceptor {

    private val logger = LoggerToolkit.getLogger(RequestLogInterceptor::class.java)

    private var logConfig : VoidConfiguration.GlobalLogConfig = voidConfiguration.globalLogConfig

    @Autowired
    private lateinit var amqpShell: AmqpShell

    init {
        logger.info("[${MODULE_NAME}] RequestLogInterceptor init success.")
    }

    /**
     * 请求前记录日志
     */
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        return this.logRequest(request, response, handler)
    }

    fun logRequest(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is ResourceHttpRequestHandler){
            if (logConfig.recordStaticResource){
                logger.info("<=STATIC-TIME:[${Time.getCurrentLocalDateTime()}]=RID:[${request.getAttribute(StandardFields.FILTER_REQUEST_ID_KEY)}]=${request.requestURI}====>")
            }
            return true
        }
        if (logConfig.recordRequest) {
            val className : String
            val methodName : String
            if (handler is HandlerMethod){
                val beanType: Class<*> = handler.beanType
                className = beanType.name
                methodName = handler.method.name
            }else if (handler is JSONObject){
                handler as JSONObject
                className = handler.getString("className")
                methodName = handler.getString("methodName")
            }else{
                className = handler.javaClass.name
                methodName = I18n.translate("commons.unknown")
            }
            logger.info("<====REQUEST-TIME:[${Time.getCurrentLocalDateTime()}]======" +
                    "RID:[${request.getAttribute(StandardFields.FILTER_REQUEST_ID_KEY)}]=====>")
            logger.info("CLASS#METHOD:${className}#${methodName}")
            logger.info("URL：${request.requestURL}\tTYPE：${request.method} Address:${RequestToolkit.getIpAddress(request)}")
            logger.info("MIME-TYPE：${request.contentType?:"-UNKNOWN-"}\tUser-Agent：${request.getHeader("User-Agent")}")
            var paramsStr = "["
            if (logConfig.recordRequestParams) {
                var idx = 0
                request.parameterMap.forEach { (key, value) ->
                    paramsStr+= "'$key = $value',"
                    logger.info("PARAM[${idx++}]：$key = ${value.joinToString(",")}")
                }
            }
            paramsStr+= "]"
            if (logConfig.recordToDb){
                val systemLog = SystemLog()
                systemLog.intro = "请求[${request.requestURI}]"
                if (request.requestURI.contains("auth")){
                    systemLog.type = LogType.AUTH.name
                }
                val details = JSONObject(true)
                details["rid"] = request.getAttribute(StandardFields.FILTER_REQUEST_ID_KEY)
                details["method"] = request.method
                details["contentType"] = request.contentType
                details["userAgent"] = request.getHeader("User-Agent")
                if (logConfig.recordRequestParams) {
                    details["params"] = paramsStr
                }
                systemLog.details = details.toJSONString()
                amqpShell.sendLog(LogType.REQUEST,systemLog)
            }
        }
        return true
    }

}