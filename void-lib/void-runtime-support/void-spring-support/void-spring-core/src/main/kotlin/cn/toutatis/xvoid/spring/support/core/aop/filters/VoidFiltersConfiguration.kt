package cn.toutatis.xvoid.spring.support.core.aop.filters

/**
 * ServletFilters Servlet过滤器注册
 */
//@Configuration
@Deprecated("ServletDispatcherType不生效,改用Spring的OncePerRequestFilter")
open class VoidFiltersConfiguration {
//
//    @Autowired
//    private lateinit var requestRidInjectFilter:RequestRidInjectFilter

    /**
     * 注册ID注入过滤器(最高优先级)
     */
//    @Bean
//    fun requestIdInjectFilterRegistrationBean(): FilterRegistrationBean<*> {
//        val registration: FilterRegistrationBean<*> = FilterRegistrationBean(requestRidInjectFilter)
//        registration.addUrlPatterns("/*")
//        registration.order = 0
//        return registration
//    }

}
