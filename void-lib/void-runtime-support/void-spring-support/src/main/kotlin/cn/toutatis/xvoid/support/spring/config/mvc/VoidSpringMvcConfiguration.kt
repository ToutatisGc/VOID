package cn.toutatis.xvoid.support.spring.config.mvc

import cn.toutatis.xvoid.support.spring.core.aop.interceptor.RequestLogInterceptor
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

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(requestLogInterceptor)
        super.addInterceptors(registry)
    }

    /**
     * 添加静态资源映射
     */
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        /*TODO 缓存*/
        registry.addResourceHandler("/static/**").addResourceLocations("/static/", "classpath:/static/")
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/favicon.ico")
        registry.addResourceHandler("/robots.txt").addResourceLocations("classpath:/robots.txt")
    }

    @Bean
    open fun requestContextListener(): RequestContextListener {
        System.err.println("requestContextListener")
        return RequestContextListener()
    }
}