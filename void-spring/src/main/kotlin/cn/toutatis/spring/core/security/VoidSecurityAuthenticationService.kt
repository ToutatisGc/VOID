package cn.toutatis.spring.core.security

import cn.toutatis.data.common.result.ResultCode
import cn.toutatis.spring.core.security.ValidationMessage.Companion.VALIDATION_SESSION_KEY
import cn.toutatis.xvoid.toolkit.http.RequestToolkit
import cn.toutatis.xvoid.toolkit.objects.ObjectToolkit
import cn.toutatis.xvoid.support.spring.config.VoidConfiguration
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession


/**
 * Spring Security核心类
 * 用户认证服务，统一登陆点
 * @author Toutatis_Gc
 */

@Service
class VoidSecurityAuthenticationService : UserDetailsService {

    private val objectToolkit  =  ObjectToolkit.getInstance()

//    private val logger = LoggerToolkit.getLogger(javaClass)

    @Autowired
    private lateinit var session: HttpSession

    @Autowired
    private lateinit var request: HttpServletRequest

    @Autowired
    private lateinit var voidConfiguration: VoidConfiguration


    enum class MessageType {
        /**
         * 字符串和JSON类型
         */
        STRING, JSON
    }


    /**
     * 全局认证入口
     * @param identity 认证信息,认证信息为JSON对象
     * 为什么有的返回信息为 ILLEGAL_OPERATION 违规操作？
     * 因为按照正常调用的话只会在浏览器或内部系统中进行认证，并且格式是固定的，
     * 出现异常情况只会在开发调试阶段，所以线上服务出现违规操作说明有渗透情况，需要及时排查
     */
    override fun loadUserByUsername(identity: String): UserDetails {
        var identityObj = JSONObject(0)
        if (objectToolkit.strNotBlank(identity)){
            try {
                identityObj = JSON.parseObject(identity)
            }catch(e:Exception){
                e.printStackTrace()
                this.throwIllegalOperation("认证信息格式错误")
            }
            if (identityObj.isNotEmpty()){
                val authTypeStr = identityObj.getString("authType")
                if (authTypeStr != null){
                    when(AuthType.valueOf(authTypeStr)){
                        AuthType.ACCOUNT_CHECK ->{

                        }
                        else ->{
                            this.throwIllegalOperation("未开放的认证方式")
                        }
                    }
                }
            }
        }else{
            this.throwIllegalOperation("认证信息为空")
        }
        TODO("Not yet implemented")
    }

    /**
     * @param msg 抛出违规操作异常,并且在handler中记录
     * @see cn.toutatis.spring.core.security.handler.SecurityHandler 认证失败处理器
     */
    private fun throwIllegalOperation(msg:String){
        val illegalOperation = ResultCode.ILLEGAL_OPERATION
        val errorInfo = JSONObject()
        errorInfo["name"] = illegalOperation.name
        errorInfo["message"] = msg
        errorInfo["ip"] = RequestToolkit.getIpAddr(request)
        throwInfo(MessageType.JSON,errorInfo)
    }

    /**
     * 抛出认证异常信息
     * @see cn.toutatis.spring.core.security.handler.SecurityHandler 认证失败处理器
     * @param validationMessage 认证异常信息
     * @param o 额外携带的信息
     * @param type 消息类型，传入JSON就会转为JSON字符串
     */
    private fun throwInfo(validationMessage: Any, o: Any?, type: MessageType) {
        val message: String = when (type) {
            MessageType.JSON -> JSON.toJSONString(validationMessage)
            else -> validationMessage.toString()
        }
        throwInfo(message, o)
    }

    private fun throwInfo(type: MessageType,validationMessage: Any ) {
        throwInfo(validationMessage, null,type)
    }

    private fun throwInfo(type: MessageType,validationMessage: String ) {
        throwInfo(validationMessage, null,type)
    }

    private fun throwInfo(validationMessage: String, obj: Any?) {
        if (obj != null) session.setAttribute(VALIDATION_SESSION_KEY, obj)
        throw UsernameNotFoundException(validationMessage)
    }
}