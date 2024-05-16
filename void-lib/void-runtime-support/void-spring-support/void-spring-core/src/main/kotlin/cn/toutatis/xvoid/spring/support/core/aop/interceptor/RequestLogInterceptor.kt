package cn.toutatis.xvoid.spring.support.core.aop.interceptor

import cn.toutatis.xvoid.common.standard.StandardFields
import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog
import cn.toutatis.xvoid.orm.base.infrastructure.enums.LogType
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration
import cn.toutatis.xvoid.spring.logger.VoidSpringLoggerSender
import cn.toutatis.xvoid.spring.support.Meta.MODULE_NAME
import cn.toutatis.xvoid.toolkit.constant.Time
import cn.toutatis.xvoid.toolkit.http.RequestToolkit
import cn.toutatis.xvoid.toolkit.locale.I18n
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import com.alibaba.fastjson.JSONObject
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler


/**
 * 请求日志生成器
 */
@Component
class RequestLogInterceptor(voidGlobalConfiguration: VoidGlobalConfiguration) : HandlerInterceptor {

    /**
     * Logger 日志
     */
    private val logger = LoggerToolkit.getLogger(RequestLogInterceptor::class.java)

    /**
     * Log config 日志配置
     */
    private var logConfig : VoidGlobalConfiguration.GlobalLogConfig = voidGlobalConfiguration.globalLogConfig

    /**
     * Amqp shell 消息队列
     */
    @Autowired
    private lateinit var loggerSender: VoidSpringLoggerSender

    init {
        logger.info("[${MODULE_NAME}] RequestLogInterceptor init success.")
    }

    /**
     * 请求前记录日志
     */
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        return this.logRequest(request, response, handler)
    }

    /**
     * Log request 日志记录方法
     *
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理者来源可能为请求/自定义JSON
     * @return true 仅为日志拦截，不对请求做控制
     */
    fun logRequest(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // 是否静态资源请求
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
//                systemLog.subType = ""
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
                loggerSender.sendLog(LogType.REQUEST,systemLog)
            }
        }
        return true
    }

}