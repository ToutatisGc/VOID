package cn.toutatis.xvoid.spring.core.exception

import cn.toutatis.xvoid.common.standard.StandardFields
import cn.toutatis.xvoid.data.common.result.ProxyResult
import cn.toutatis.xvoid.data.common.result.ResultCode
import cn.toutatis.xvoid.support.spring.config.RunMode
import cn.toutatis.xvoid.support.spring.config.VoidConfiguration
import cn.toutatis.xvoid.toolkit.constant.Time
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
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
class VoidExceptionAdviceDispose {

    private val logger = LoggerToolkit.getLogger(javaClass)

    @Autowired
    private lateinit var voidConfiguration: VoidConfiguration

    @ResponseBody
    @ExceptionHandler(Exception::class)
    fun errorMsg(request: HttpServletRequest, response: HttpServletResponse, e: Exception): ProxyResult {
        logger.error("请求错误地址:{}",request.requestURL)
        val proxyResult = ProxyResult(ResultCode.REQUEST_EXCEPTION)

        val requestId = request.getAttribute(StandardFields.FILTER_REQUEST_ID)
        if (requestId != null) proxyResult.requestId = requestId as String

        if (voidConfiguration.mode == RunMode.DEBUG) {
            e.printStackTrace()
            val exceptionObj = JSONObject(3)
            exceptionObj["createTime"] = Time.currentTime
            exceptionObj["position"] = e.stackTrace[0]
            exceptionObj["reason"] = e.message
            proxyResult.data = exceptionObj
            /*TODO 异步存储*/
        }

        response.status = 500
        return proxyResult
    }
}