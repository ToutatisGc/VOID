package cn.toutatis.xvoid.support.spring.config.mvc

import cn.toutatis.xvoid.support.spring.core.aop.interceptor.RequestLogInterceptor
import cn.toutatis.xvoid.support.spring.enhance.mapping.XvoidMappingResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.request.RequestContextListener
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
open class VoidSpringMvcConfiguration : WebMvcConfigurer {

    @Autowired
    private lateinit var requestLogInterceptor: RequestLogInterceptor

    @Autowired
    private lateinit var xvoidMappingResolver: XvoidMappingResolver

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(requestLogInterceptor)
        super.addInterceptors(registry)
    }

    /**
     * 添加静态资源映射
     */
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        /*TODO 缓存*/
        for (resourcesMapping in xvoidMappingResolver.staticResourcesMapping) {
            for (location: String in resourcesMapping.locations) {
                registry.addResourceHandler(resourcesMapping.mapping).addResourceLocations(location)
            }
        }
    }

    @Bean
    open fun requestContextListener(): RequestContextListener {
        return RequestContextListener()
    }
}