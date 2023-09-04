package cn.toutatis.xvoid.spring.core.security

import cn.toutatis.xvoid.common.standard.HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS
import cn.toutatis.xvoid.common.standard.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS
import cn.toutatis.xvoid.common.standard.HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS
import cn.toutatis.xvoid.common.standard.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN
import cn.toutatis.xvoid.common.standard.HttpHeaders.ACCESS_CONTROL_MAX_AGE
import cn.toutatis.xvoid.common.standard.HttpHeaders.VOID_MCH
import cn.toutatis.xvoid.common.standard.HttpHeaders.VOID_TOKEN
import com.alibaba.fastjson.JSON
import java.io.IOException
import java.net.URLEncoder
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/*TODO 移动到support http包下*/
class VoidResponse {


    companion object{

        fun cors(request: HttpServletRequest,response: HttpServletResponse){
            cors(request, response,method = "POST,GET,OPTIONS")
        }

        fun cors(request: HttpServletRequest,response: HttpServletResponse,
                 method:String? = "POST,GET,OPTIONS",
                 allowCredentials:Boolean? = true,
                 allowHeaders:String? = "$VOID_TOKEN,$VOID_MCH,$ACCESS_CONTROL_ALLOW_HEADERS"){
            response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader("Origin"))
            response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, allowCredentials.toString())
            response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, method)
            response.setHeader(ACCESS_CONTROL_MAX_AGE, "3600")
            response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS,allowHeaders)
            if (request.method == "POST"){
                response.contentType = "application/json"
            }
            response.characterEncoding = "UTF-8"
        }

        fun returnJson(request:HttpServletRequest, response:HttpServletResponse, any:Any){
            returnJson(request = request,response = response,any = any ,method = "POST,GET,OPTIONS")
        }

        fun returnJson(request:HttpServletRequest,
                       response:HttpServletResponse,
                       any:Any?,method:String? = "POST,GET,OPTIONS",
                       allowCredentials:Boolean? = true,
                       allowHeaders:String? = "$VOID_TOKEN,$VOID_MCH,$ACCESS_CONTROL_ALLOW_HEADERS"
                       ){
            cors(request,response,method,allowCredentials,allowHeaders)
            try {
                response.outputStream.use { outputStream ->
                    outputStream.write(JSON.toJSONBytes(any))
                    outputStream.flush()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun redirect(path:String,httpServletRequest: HttpServletRequest,httpServletResponse: HttpServletResponse){
            if (httpServletRequest.method == "GET"){
                httpServletResponse.sendRedirect(URLEncoder.encode("${httpServletRequest.contextPath}${path}","UTF-8"))
            }
        }
    }
}