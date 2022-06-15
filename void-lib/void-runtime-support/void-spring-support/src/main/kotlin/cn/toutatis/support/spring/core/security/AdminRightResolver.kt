package cn.toutatis.support.spring.core.security

import cn.toutatis.support.spring.config.VoidConfiguration
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper
import com.baomidou.mybatisplus.core.conditions.Wrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

/**
 * @author Toutatis_Gc
 * 超级管理员权限解析器
 * 此类用于多权限分级下，区分管理员和子用户，保证超级管理员可获取所有数据
 */
@Component
class AdminRightResolver {

    @Autowired
    private lateinit var voidConfiguration : VoidConfiguration

    /**
     * 是否是平台模式，平台模式下需要区分子用户
     */
    private final val platformMode = voidConfiguration.platformMode

    fun <T> queryCondition(request: HttpServletRequest,wrapper:Wrapper<T>): Unit {
        /*TODO 必须先完成鉴权才能进行后续*/
        if (platformMode){

        }
    }

}