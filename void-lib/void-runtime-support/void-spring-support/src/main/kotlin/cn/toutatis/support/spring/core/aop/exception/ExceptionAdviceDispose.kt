package cn.toutatis.spring.core.exception

import cn.toutatis.data.common.ProxyResult
import cn.toutatis.data.common.ResultCode
import cn.toutatis.support.spring.config.VoidConfiguration
import cn.toutatis.toolkit.log.LoggerToolkit
import com.alibaba.fastjson.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 异常处理器
 */
@ControllerAdvice
class ExceptionAdviceDispose {

    private val logger = LoggerToolkit.getLogger(javaClass)

    @Autowired
    private lateinit var voidConfiguration: VoidConfiguration

    @ResponseBody
    @ExceptionHandler(Exception::class)
    fun errorMsg(request: HttpServletRequest, response: HttpServletResponse, e: Exception): ProxyResult {
        logger.error("请求错误地址:{}",request.requestURI)
        val debug = voidConfiguration.debug
        if (debug) {
            e.printStackTrace()
            val exceptionObj = JSONObject(3)
        }

//        exceptionObj["createTime"] = TimeConstant.INSTANCE.currentTime
//        exceptionObj["position"] = e.stackTrace[0]
//        exceptionObj["reason"] = e.message
//        voidProducer.sendError(exceptionObj)
//        val result = Result(false)
//        val requestId = request.getAttribute(LogBackInterceptor.REQUEST_ID_KEY) as String
//        result.requestId = requestId
//        result.setCode(ResultCode.REQUEST_EXCEPTION)
//        result.setMessage(ResultCode.REQUEST_EXCEPTION)
        response.status = 500
        return ProxyResult(ResultCode.REQUEST_EXCEPTION,null,null)
    }
}