package cn.toutatis.xvoid.support.spring.doc

import cn.toutatis.xvoid.support.spring.config.VoidConfiguration
import org.springframework.beans.BeansException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.ReflectionUtils
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider
import springfox.documentation.swagger2.annotations.EnableSwagger2


/**
 * Void knife4j configuration
 *
 * @constructor Create empty Void knife4j configuration
 */
@Configuration
@EnableSwagger2
@EnableWebMvc
open class VoidKnife4JConfiguration {

    @Autowired
    private lateinit var voidConfiguration : VoidConfiguration

    @Bean("apiDocketBean")
    @ConditionalOnMissingBean
    fun apiDocketBean(): Docket {
        val docConfig = voidConfiguration.docConfig
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(ApiInfoBuilder()
                .description(docConfig.description)
                .title(docConfig.title)
                .contact(Contact("Toutatis_Gc","www.xvoid.cn","gc@toutatis.cn"))
                .version("0.0.0-ALPHA")
                .build())
            .groupName("默认文档")
            .select()
            .apis(RequestHandlerSelectors.basePackage(docConfig.basePackage))
            .paths(PathSelectors.any())
            .build()
    }

    /**
     * Conflict with actuator
     * 与actuator冲突
     * ISSUE https://gitee.com/xiaoym/knife4j/issues/I4JT89
     */
    @Bean
    fun springfoxHandlerProviderBeanPostProcessor(): BeanPostProcessor {
        return object : BeanPostProcessor {
            @Throws(BeansException::class)
            override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
                if (bean is WebMvcRequestHandlerProvider || bean is WebFluxRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean))
                }
                return bean
            }

            private fun <T : RequestMappingInfoHandlerMapping> customizeSpringfoxHandlerMappings(mappings: MutableList<T>) {
                mappings.removeIf { mapping: T -> mapping.patternParser != null }
            }

            private fun getHandlerMappings(bean: Any): MutableList<RequestMappingInfoHandlerMapping> {
                return try {
                    val findField = ReflectionUtils.findField(bean.javaClass, "handlerMappings")
                    findField?.isAccessible = true
                    findField?.get(bean) as MutableList<RequestMappingInfoHandlerMapping>
                } catch (e: IllegalArgumentException) {
                    throw IllegalStateException(e)
                } catch (e: IllegalAccessException) {
                    throw IllegalStateException(e)
                }
            }
        }
    }
}