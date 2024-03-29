package cn.toutatis.xvoid.spring.core.security.access

import cn.toutatis.xvoid.common.enums.AuthType
import cn.toutatis.xvoid.common.enums.MessageType
import cn.toutatis.xvoid.common.result.ResultCode
import cn.toutatis.xvoid.common.standard.AuthFields
import cn.toutatis.xvoid.common.standard.AuthValidationMessage
import cn.toutatis.xvoid.common.standard.HttpHeaders
import cn.toutatis.xvoid.common.standard.StandardFields
import cn.toutatis.xvoid.orm.base.authentication.entity.RequestAuthEntity
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration
import cn.toutatis.xvoid.spring.core.security.access.auth.LocalUserService
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.validator.Validator
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.github.xiaoymin.knife4j.annotations.ApiSupport
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
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

@ApiSupport(order = 0, author = "Toutatis_Gc")
@Service
@SecurityScheme(
    name = HttpHeaders.VOID_AUTHENTICATION,
    type = SecuritySchemeType.APIKEY,
    `in` = SecuritySchemeIn.HEADER,
    paramName = "请求接口必须携带Http-Header")
class VoidSecurityAuthenticationService : UserDetailsService {



    private val logger = LoggerToolkit.getLogger(javaClass)

    @Autowired
    lateinit var request: HttpServletRequest

//    @Autowired
//    lateinit var listener:RequestContextListener

    @Autowired
    private lateinit var voidGlobalConfiguration: VoidGlobalConfiguration

    @Autowired
    private lateinit var localUserService: LocalUserService

    @Autowired
    private lateinit var httpSession: HttpSession

    /**
     * 全局认证入口
     * 该入口校验认证信息序列化是否有误
     * 并由此入口分发到各个认证服务进行身份认证
     *
     * 为什么有的返回信息为 ILLEGAL_OPERATION 违规操作？
     * 因为按照正常调用的话只会在浏览器或内部系统中进行认证，并且格式是固定的，
     * 出现异常情况只会在开发调试阶段，所以线上服务出现违规操作说明有渗透情况，需要及时排查
     * @param identity 认证信息,认证信息为JSON对象
     */

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(identity: String): UserDetails {
        // 校验是否为空
        if (Validator.strNotBlank(identity)){
            val requestAuthEntity: RequestAuthEntity
            try {
                // 尝试序列化对象
                val identityObj = JSON.parseObject(identity)
                // 校验是否存在必填参数
                if (voidGlobalConfiguration.isDebugging){
                    Validator.checkMapContainsKeyThrowEx(identityObj, AuthFields.ACCOUNT,AuthFields.AUTH_TYPE)
                }else{
                    if (!Validator.checkMapContainsKeyBoolean(identityObj, AuthFields.ACCOUNT,AuthFields.AUTH_TYPE)){
                        throw this.throwIllegalOperation(AuthValidationMessage.PARAMETER_ERROR)
                    }
                }
                requestAuthEntity = RequestAuthEntity(identityObj)
            }catch(e:Exception){
                if (voidGlobalConfiguration.isDebugging){
                    throw this.throwIllegalOperation(e.message.toString())
                }
                throw this.throwIllegalOperation(AuthValidationMessage.WRONG_IDENTIFY_FORMAT)
            }
            val authType = requestAuthEntity.authType
            if (authType != null){
                // 核心认证,分发到各个业务类型认证
                when(authType){
                    AuthType.ACCOUNT_NORMAL ->{
                        return localUserService.findSimpleUser(requestAuthEntity)
                    }
                    else ->{
                        throw this.throwIllegalOperation(AuthValidationMessage.NOT_OPENED_IDENTIFY_TYPE)
                    }
                }
            }else{
                throw this.throwIllegalOperation(AuthValidationMessage.WRONG_IDENTIFY_TYPE)
            }
        }else{
            throw this.throwIllegalOperation(AuthValidationMessage.REQUIRED_IDENTIFY_INFO)
        }
    }

    /**
     * @param securityCode 验证码
     * 验证码校验
     * 验证码存储在session中
     * @return 是否通过验证
     */
    private fun checkCodeEquals(securityCode: String?): Boolean {
        if (Validator.strIsBlank(securityCode)) {
            return false
        }
        val session: HttpSession = request.session
        val sessionSecurityCode = session.getAttribute(AuthFields.VALIDATION_SECURITY_CODE_SESSION_KEY) as String?
        session.removeAttribute(AuthFields.VALIDATION_SECURITY_CODE_SESSION_KEY)
        if (Validator.strIsBlank(sessionSecurityCode)) {
            return false
        }
        return sessionSecurityCode.equals(securityCode, ignoreCase = true)
    }

    /**
     * @param msg 抛出违规操作异常,并且在handler中记录
     * @see cn.toutatis.xvoid.spring.core.security.config.handler.SecurityHandler 认证失败处理器
     */
    @Throws(UsernameNotFoundException::class)
    private fun throwIllegalOperation(msg:String):UsernameNotFoundException{
        val illegalOperation = ResultCode.ILLEGAL_OPERATION
        val errorInfo = JSONObject()
        errorInfo["name"] = illegalOperation.name
        errorInfo["message"] = msg
        return throwInfo(MessageType.JSON,errorInfo)
    }

    /**
     * 抛出认证异常信息
     * @see cn.toutatis.spring.core.security.handler.SecurityHandler 认证失败处理器
     * @param validationMessage 认证异常信息
     * @param o 额外携带的信息
     * @param type 消息类型，传入JSON就会转为JSON字符串
     */
    @Throws(UsernameNotFoundException::class)
    private fun throwInfo(type: MessageType, validationMessage: Any) :UsernameNotFoundException{
        request.setAttribute(StandardFields.VOID_HTTP_ATTRIBUTE_MESSAGE_KEY,validationMessage)
        throw UsernameNotFoundException(type.name)
    }

}