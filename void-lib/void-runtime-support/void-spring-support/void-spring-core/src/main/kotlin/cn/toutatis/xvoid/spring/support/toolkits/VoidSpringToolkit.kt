package cn.toutatis.xvoid.spring.support.toolkits

import cn.toutatis.xvoid.common.standard.StandardFields
import cn.toutatis.xvoid.toolkit.validator.Validator
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest


/**
 * @author Toutatis_Gc
 * spring bean 工具类
 */
@Component
class VoidSpringToolkit : ApplicationContextAware {

    private lateinit var applicationContext: ApplicationContext

    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }

    fun <T> getBean(beanName: String): T? {
        return if (applicationContext.containsBean(beanName)) {
            applicationContext.getBean(beanName) as T
        } else {
            null
        }
    }

    fun <T> getBeansOfType(baseType: Class<T>): Map<String?, T> {
        return applicationContext.getBeansOfType(baseType)
    }

    fun getRid(httpServletRequest: HttpServletRequest):String{
        val rid = httpServletRequest.getAttribute(StandardFields.FILTER_REQUEST_ID_KEY) as String
        return if (Validator.strIsBlank(rid)){ "-" }else{ rid }
    }

}