package cn.toutatis.xvoid.spring.support.core.aop.exception

import cn.toutatis.xvoid.common.exception.IllegalException
import cn.toutatis.xvoid.common.result.ProxyResult
import cn.toutatis.xvoid.common.result.ResultCode
import cn.toutatis.xvoid.common.standard.StandardFields
import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog
import cn.toutatis.xvoid.orm.base.infrastructure.enums.LogType
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration
import cn.toutatis.xvoid.spring.configure.system.enums.global.RunMode
import cn.toutatis.xvoid.spring.logger.VoidSpringLoggerSender
import cn.toutatis.xvoid.spring.support.Meta
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.errorWithModule
import com.alibaba.fastjson.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 异常处理器
 */
@ControllerAdvice
class VoidExceptionAdviceDispose {

    private val logger = LoggerToolkit.getLogger(javaClass)

    @Autowired
    private lateinit var voidGlobalConfiguration: VoidGlobalConfiguration

    @Autowired
    private lateinit var loggerSender: VoidSpringLoggerSender

    /**
     * 异常处理器
     * MethodArgumentTypeMismatchException 该异常在接收参数时未转换至所需枚举类,通常发生在重放和爬取接口
     * @param request 请求
     * @param response 响应
     * @param e 异常信息
     * @return 异常返回信息
     */
    @ResponseBody
    @ExceptionHandler(Exception::class,Throwable::class)
    fun errorMsg(request: HttpServletRequest, response: HttpServletResponse, e: Exception): ProxyResult {
        response.status = 500
        logger.error("请求错误地址:{},[Exception:{}]",request.requestURL,e.toString())
        var proxyResult = ProxyResult(ResultCode.REQUEST_EXCEPTION)
        val requestId = request.getAttribute(StandardFields.FILTER_REQUEST_ID_KEY)
        if (requestId != null) proxyResult.requestId = requestId as String
        when (e) {
            // URL无法解析到方法
            is NoHandlerFoundException -> {
                proxyResult = ProxyResult(ResultCode.NOT_FOUND)
                proxyResult.supportMessage = "[${request.requestURI}]${ResultCode.NOT_FOUND.info}"
            }
            // Http方法GET,POST错误
            is HttpRequestMethodNotSupportedException -> {
                /*一般情况下,用户按照既定路径访问,不会出现访问方法错误的问题*/
                proxyResult = ProxyResult(ResultCode.ILLEGAL_OPERATION)
                proxyResult.supportMessage="URI:[${request.requestURI}]不支持[${request.method}]方法"
            }
            // 违规访问错误和枚举类转换错误
            is IllegalException, is MethodArgumentTypeMismatchException -> {
                proxyResult = ProxyResult(ResultCode.ILLEGAL_OPERATION)
                if (voidGlobalConfiguration.isDebugging){ proxyResult.supportMessage=e.message }
                e.message?.let { logger.errorWithModule(Meta.MODULE_NAME,"EXCEPTION", it) }
            }
            // 请求缺失参数异常
            is MissingServletRequestParameterException ->{
                proxyResult = ProxyResult(ResultCode.MISSING_PARAMETER)
                val err = "请求[${request.requestURI}]缺失参数[${e.parameterType}:${e.parameterName}]"
                if (voidGlobalConfiguration.mode == RunMode.DEBUG || voidGlobalConfiguration.mode == RunMode.DEV){
                    proxyResult.supportMessage= err
                }else{
                    logger.errorWithModule(Meta.MODULE_NAME,"EXCEPTION",err)
                }
            }
            else -> {
                logger.errorWithModule(Meta.MODULE_NAME,"EXCEPTION","${e::class}未捕获异常,请尽快处理.")
                e.printStackTrace()
            }
        }
        if (voidGlobalConfiguration.globalLogConfig.recordToDb){
            val systemLog = SystemLog()
            systemLog.type = LogType.EXCEPTION.name
            systemLog.intro = "异常[${e::class.simpleName}]"
            systemLog.details = parseErrorJson(e,request).toJSONString()
            if (e.javaClass == IllegalException::class.java){
                loggerSender.sendLog(LogType.ILLEGAL,systemLog)
            }else{
                loggerSender.sendLog(LogType.EXCEPTION,systemLog)
            }

        }
        if (voidGlobalConfiguration.mode == RunMode.DEBUG) {
            e.printStackTrace()
            val parseErrorJson = parseErrorJson(e,request)
            proxyResult.data = parseErrorJson
        }
        return proxyResult
    }

    protected fun parseErrorJson(e: Exception,request: HttpServletRequest):JSONObject{
        val exceptionJson = JSONObject(3)
        val stackTraceElement = e.stackTrace[0]
        val position = if (stackTraceElement.className.startsWith(cn.toutatis.xvoid.common.Meta.BASE_PACKAGE)){
            "${stackTraceElement.className}.${stackTraceElement.methodName}[line ${stackTraceElement.lineNumber}]"
        }else{
            stackTraceElement.className
        }
        exceptionJson["position"] = "$position[line ${stackTraceElement.lineNumber}]"
        exceptionJson["reason"] = e.message
        exceptionJson["uri"] = request.requestURI
        return exceptionJson
    }
}