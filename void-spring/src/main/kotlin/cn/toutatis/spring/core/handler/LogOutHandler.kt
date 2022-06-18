package cn.toutatis.core.root.security.handler

import cn.toutatis.data.common.result.ProxyResult
import cn.toutatis.data.common.result.ResultCode
import com.alibaba.fastjson.JSON
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LogOutHandler : LogoutSuccessHandler {

    override fun onLogoutSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val principal = authentication.principal
        val result: ProxyResult =
                if (principal is String){
                    ProxyResult(ResultCode.NOT_LOGGED_IN_ERROR)
                }else {
                    ProxyResult(ResultCode.LOGOUT_SUCCESSFUL)
                }
        returnJson(request,response, result)
    }

    /**
     * @param response  response
     * @param o 序列化实体类
     * @throws IOException 流异常
     */
    @Throws(IOException::class)
    private fun returnJson(request: HttpServletRequest,response: HttpServletResponse, o: Any) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"))
        response.setHeader("Access-Control-Allow-Credentials", "true")
        response.setHeader("Access-Control-Allow-Methods", "POST, GET")
        response.setHeader("Access-Control-Max-Age", "3600")
        response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Authorization, X-Requested-With, Cookie")
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer.write(JSON.toJSONString(o))
    }

}