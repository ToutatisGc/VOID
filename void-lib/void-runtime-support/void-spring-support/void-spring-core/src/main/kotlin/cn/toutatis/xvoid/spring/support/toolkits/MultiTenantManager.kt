package cn.toutatis.xvoid.spring.support.toolkits

import cn.toutatis.xvoid.common.exception.IllegalException
import cn.toutatis.xvoid.common.exception.MissingParameterException
import cn.toutatis.xvoid.common.standard.StandardFields
import cn.toutatis.xvoid.spring.configure.system.VoidConfiguration
import cn.toutatis.xvoid.spring.support.Meta
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.errorWithModule
import cn.toutatis.xvoid.toolkit.validator.Validator.strIsBlank
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class MultiTenantManager {

    private final val logger = LoggerToolkit.getLogger(javaClass)

    @Autowired
    private lateinit var voidConfiguration: VoidConfiguration

    @Autowired
    private lateinit var httpServletRequest: HttpServletRequest

    /**
     * Check platform
     * 检查是否为平台模式,并返回商户号
     * @return 商户号
     */
    fun checkPlatform(): String? {
        if (voidConfiguration.platformMode) {
            val mchId = httpServletRequest.getHeader(StandardFields.VOID_REQUEST_HEADER_MCH_ID)
            if (strIsBlank(mchId)) throw MissingParameterException("缺失商户ID")
            if (StandardFields.VOID_BUSINESS_DEFAULT_CREATOR == mchId) return mchId
            if (mchId.length < 16 || mchId.length > 32){
                throw IllegalException(logger.errorWithModule(Meta.MODULE_NAME,"Tenant","错误的商户ID [${mchId}]"))
            }
            return mchId
        }
        return null
    }

    fun <T> setBelongTo(wrapper:QueryWrapper<T>){
        val mchId = checkPlatform()
        if (mchId != null) wrapper.eq("belongTo",mchId)
    }

}