package cn.toutatis.xvoid.support.spring.config.mvc

import cn.toutatis.xvoid.support.spring.core.aop.interceptor.RequestLogInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
open class VoidSpringMvcConfiguration : WebMvcConfigurer {

    @Autowired
    private lateinit var requestLogInterceptor: RequestLogInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(requestLogInterceptor)
        super.addInterceptors(registry)
    }
}