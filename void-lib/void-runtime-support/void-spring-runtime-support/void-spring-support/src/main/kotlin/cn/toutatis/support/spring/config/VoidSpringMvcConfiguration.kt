package cn.toutatis.support.spring.config

import cn.toutatis.support.spring.aop.interceptor.RequestLogInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class VoidSpringMvcConfiguration : WebMvcConfigurer {

    @Autowired
    private lateinit var requestLogInterceptor: RequestLogInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(requestLogInterceptor)
        super.addInterceptors(registry)
    }
}