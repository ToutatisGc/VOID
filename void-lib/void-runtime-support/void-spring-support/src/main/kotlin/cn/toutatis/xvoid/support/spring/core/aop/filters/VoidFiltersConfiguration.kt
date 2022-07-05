package cn.toutatis.xvoid.support.spring.core.aop.filters

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * ServletFilters Servlet过滤器注册
 */
@Configuration
open class VoidFiltersConfiguration {

    @Autowired
    private lateinit var requestRidInjectFilter:RequestRidInjectFilter

    /**
     * 注册ID注入过滤器(最高优先级)
     */
    @Bean
    fun requestIdInjectFilterRegistrationBean(): FilterRegistrationBean<*> {
        val registration: FilterRegistrationBean<*> = FilterRegistrationBean(requestRidInjectFilter)
        registration.addUrlPatterns("/*")
        registration.order = 0
        return registration
    }

}
