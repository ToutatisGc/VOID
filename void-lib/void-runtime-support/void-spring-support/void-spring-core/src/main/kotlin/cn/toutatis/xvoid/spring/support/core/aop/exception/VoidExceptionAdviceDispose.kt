package cn.toutatis.xvoid.spring.support.core.aop.exception

import cn.toutatis.xvoid.common.exception.IllegalException
import cn.toutatis.xvoid.common.standard.StandardFields
import cn.toutatis.xvoid.common.result.ProxyResult
import cn.toutatis.xvoid.common.result.ResultCode
import cn.toutatis.xvoid.spring.configure.system.enums.global.RunMode
import cn.toutatis.xvoid.spring.configure.system.VoidConfiguration
import cn.toutatis.xvoid.toolkit.constant.Time
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import com.alibaba.fastjson.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 异常处理器
 */
@ControllerAdvice
class VoidExceptionAdviceDispose {

    private val logger = LoggerToolkit.getLogger(javaClass)

    @Autowired
    private lateinit var voidConfiguration: VoidConfiguration

    @ResponseBody
    @ExceptionHandler(Exception::class)
    fun errorMsg(request: HttpServletRequest, response: HttpServletResponse, e: Exception): ProxyResult {
        response.status = 500
        logger.error("请求错误地址:{},[Exception:{}]",request.requestURL,e.toString())
        var proxyResult = ProxyResult(ResultCode.REQUEST_EXCEPTION)
        val requestId = request.getAttribute(StandardFields.FILTER_REQUEST_ID_KEY)
        if (requestId != null) proxyResult.requestId = requestId as String
        if (e is HttpRequestMethodNotSupportedException){
            proxyResult = ProxyResult(ResultCode.ILLEGAL_OPERATION)
            proxyResult.supportMessage="URI:[${request.requestURI}]不支持[${request.method}]方法"
        }else if (e is IllegalException){
            proxyResult = ProxyResult(ResultCode.ILLEGAL_OPERATION)
            proxyResult.supportMessage=e.message
        } else{
            e.printStackTrace()
        }
        if (voidConfiguration.mode == RunMode.DEBUG) {
            e.printStackTrace()
            val exceptionObj = JSONObject(3)
            exceptionObj["createTime"] = Time.currentTime
            exceptionObj["position"] = e.stackTrace[0]
            exceptionObj["reason"] = e.message
            proxyResult.data = exceptionObj
            /*TODO 异步存储*/
        }
        return proxyResult
    }
}