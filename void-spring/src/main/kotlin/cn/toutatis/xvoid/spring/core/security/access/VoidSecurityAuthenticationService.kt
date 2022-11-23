package cn.toutatis.xvoid.spring.core.security.access

import cn.toutatis.xvoid.common.standard.StandardFields
import cn.toutatis.xvoid.data.common.result.ResultCode
import cn.toutatis.xvoid.spring.core.security.access.persistence.SystemUserLoginMapper
import cn.toutatis.xvoid.spring.core.security.access.service.FormUserAuthService
import cn.toutatis.xvoid.support.spring.config.VoidConfiguration
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.validator.Validator
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.github.xiaoymin.knife4j.annotations.ApiSupport
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
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

@Api(tags = ["权限API"], description = "权限部分接口", basePath = "/auth")
@ApiSupport(order = 0, author = "Toutatis_Gc")
@Service
class VoidSecurityAuthenticationService : UserDetailsService {

    companion object{

        /**
         * 验证码session key
         */
        const val VALIDATION_SECURITY_CODE_SESSION_KEY = "VOID_VALIDATION_SECURITY_CODE_SESSION_KEY"
    }


    private val logger = LoggerToolkit.getLogger(javaClass)

    @Autowired
    private lateinit var systemUserLoginMapper: SystemUserLoginMapper

    @Autowired
    lateinit var request: HttpServletRequest

//    @Autowired
//    lateinit var listener:RequestContextListener

    @Autowired
    private lateinit var voidConfiguration: VoidConfiguration

    @Autowired
    private lateinit var formUserAuthService : FormUserAuthService


    /**
     * handler中获取的消息类型
     */
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
    @ApiOperation("用户登录")
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(identity: String): UserDetails {
        System.err.println(request.session.id)
        if (Validator.strNotBlank(identity)){
            val identityObj: JSONObject
            try {
                identityObj = JSON.parseObject(identity)
            }catch(e:Exception){
                throw this.throwIllegalOperation(ValidationMessage.WRONG_IDENTIFY_FORMAT)
            }
            if (identityObj != null && identityObj.isNotEmpty()){
                val authTypeStr = identityObj.getString("authType")
                if (authTypeStr != null){
                    /*TODO 认证*/
                    when(AuthType.valueOf(authTypeStr)){
                        AuthType.ACCOUNT_NORMAL ->{
                            return formUserAuthService.findSimpleUser(identityObj.getString("username"))
                        }
                        else ->{
                            throw this.throwIllegalOperation(ValidationMessage.NOT_OPENED_IDENTIFY_TYPE)
                        }
                    }
                }else{
                    throw this.throwIllegalOperation(ValidationMessage.WRONG_IDENTIFY_TYPE)
                }
            }else{
                throw this.throwIllegalOperation(ValidationMessage.REQUIRED_IDENTIFY_PAYLOAD)
            }
        }else{
            throw this.throwIllegalOperation(ValidationMessage.REQUIRED_IDENTIFY_INFO)
        }
    }

    /**
     * 账号密码认证
     * @param identityObj 认证信息
     *        account 账号
     *        password 密码
     *        securityCode 验证码
     * @return 认证结果
     */
//    @Throws(UsernameNotFoundException::class)
//    private fun findAccountCheckUser(identityObj:JSONObject):UserDetails{
//        val check = checkCodeEquals(identityObj.getString("securityCode"))
//        if (check) {
//            val username = identityObj.getString("username")
//            if (Validator.strIsBlank(username)){
//                throw this.throwInfo(MessageType.STRING, ValidationMessage.USERNAME_BLANK)
//            }
//            /*用户可以使用邮箱/手机号/账号登录*/
//            val queryWrapper = QueryWrapper<SystemUserLogin>()
//            queryWrapper
//                .eq("username", username)
//                .or().eq("email", username)
//                .or().eq("phone", username)
//            val user = systemUserLoginMapper.selectOne(queryWrapper)
//            if (user != null){
//                val accountCheckUserDetails = user as AccountCheckUserDetails
//                accountCheckUserDetails.isEnabled = user.status == DataStatus.SYS_OPEN_0000
////                accountCheckUserDetails.
//                return accountCheckUserDetails
//            }else{
//                throw this.throwInfo(MessageType.STRING, ValidationMessage.USER_NOT_EXIST)
//            }
//        }else{
//            throw this.throwInfo(MessageType.STRING, ValidationMessage.CHECK_CODE_ERROR)
//        }
//    }

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
        val sessionSecurityCode = session.getAttribute(VALIDATION_SECURITY_CODE_SESSION_KEY) as String?
        session.removeAttribute(VALIDATION_SECURITY_CODE_SESSION_KEY)
        if (Validator.strIsBlank(sessionSecurityCode)) {
            return false
        }
        return sessionSecurityCode.equals(securityCode, ignoreCase = true)
    }

    /**
     * @param msg 抛出违规操作异常,并且在handler中记录
     * @see cn.toutatis.xvoid.spring.core.security.core.handler.SecurityHandler 认证失败处理器
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