package cn.toutatis.support.spring.doc

import org.springframework.beans.BeansException
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
 * @author Toutatis_Gc
 * @date 2022/5/19 22:49
 * springfox文档加载配置
 */
@Configuration
@EnableSwagger2
@EnableWebMvc
class VoidKnife4JConfiguration {

    @Bean("apiDocketBean")
    @ConditionalOnMissingBean
    fun apiDocketBean(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(ApiInfoBuilder()
                .description("RESTful APIs")
                .title("VOID文档")
                .contact(Contact("Toutatis_Gc","www.xvoid.cn","gc@toutatis.cn"))
                .version("0.0.0-ALPHA")
                .build())
            .groupName("默认文档")
            .select()
            .apis(RequestHandlerSelectors.basePackage("cn.toutatis"))
            .paths(PathSelectors.any())
            .build()
    }

    /**
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