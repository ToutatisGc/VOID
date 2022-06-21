package cn.toutatis.spring.core.security

import cn.toutatis.spring.core.security.ValidationMessage.Companion.VALIDATION_SESSION_KEY
import cn.toutatis.toolkit.objects.ObjectToolkit
import com.alibaba.fastjson.JSON
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import javax.servlet.http.HttpSession


/**
 * 用户认证服务，统一登陆点
 * @author Toutatis_Gc
 */

@Service
class VoidSecurityAuthenticationService : UserDetailsService {

    private val objectToolkit  =  ObjectToolkit.getInstance()

    @Autowired
    private lateinit var session:HttpSession


    enum class MessageType {
        /**
         * 字符串和JSON类型
         */
        STRING, JSON
    }


    /**
     * 认证入口
     * 传入参数为JSON对象
     */
    override fun loadUserByUsername(identity: String): UserDetails {
       if (objectToolkit.strNotBlank(identity)){

       }else{
//           throwInfo()
       }
        TODO("Not yet implemented")
    }

    private fun throwInfo(validationMessage: String, o: Any, type: MessageType) {
        val message: String
        message = when (type) {
            MessageType.JSON -> JSON.toJSONString(validationMessage)
            else -> validationMessage
        }
        throwInfo(message, o)
    }

    private fun throwInfo(validationMessage: String, obj: Any?) {
        if (obj != null) {
            session.setAttribute(VALIDATION_SESSION_KEY, obj)
        }
        throw UsernameNotFoundException(validationMessage)
    }
}