package cn.toutatis.xvoid.spring.support.doc

import cn.toutatis.xvoid.spring.configure.system.VoidConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType
import org.springframework.boot.actuate.endpoint.ExposableEndpoint
import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver
import org.springframework.boot.actuate.endpoint.web.EndpointMapping
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes
import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.util.StringUtils
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket


/**
 * Void knife4j configuration
 *
 * @constructor Create empty Void knife4j configuration
 */
@Configuration
@EnableWebMvc
class VoidKnife4JConfiguration {

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

    @Bean
    fun webEndpointServletHandlerMapping(
        webEndpointsSupplier: WebEndpointsSupplier,
        servletEndpointsSupplier: ServletEndpointsSupplier,
        controllerEndpointsSupplier: ControllerEndpointsSupplier,
        endpointMediaTypes: EndpointMediaTypes,
        corsProperties: CorsEndpointProperties,
        webEndpointProperties: WebEndpointProperties,
        environment: Environment,
    ): WebMvcEndpointHandlerMapping {
        val allEndpoints = ArrayList<ExposableEndpoint<*>?>()
        val webEndpoints = webEndpointsSupplier.endpoints
        allEndpoints.addAll(webEndpoints)
        allEndpoints.addAll(servletEndpointsSupplier.endpoints)
        allEndpoints.addAll(controllerEndpointsSupplier.endpoints)
        val basePath = webEndpointProperties.basePath
        val endpointMapping = EndpointMapping(basePath)
        val shouldRegisterLinksMapping = shouldRegisterLinksMapping(webEndpointProperties, environment, basePath)
        return WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsProperties.toCorsConfiguration(), EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping, null)
    }

    private fun shouldRegisterLinksMapping(
        webEndpointProperties: WebEndpointProperties, environment: Environment, basePath: String,
    ): Boolean {
        return webEndpointProperties.discovery.isEnabled && (StringUtils.hasText(basePath) || ManagementPortType.get(environment) == ManagementPortType.DIFFERENT)
    }

//    @Bean
//    open fun openAPI(): OpenAPI {
//        val docConfig = voidConfiguration.docConfig
//        return OpenAPI()
//            .info(Info()
//                .contact(Contact()
//                    .name(Meta.ADMINISTRATOR)
//                    .email("gc@toutatis.cn")
//                    .url("https://github.com/ToutatisGc")
//                )
//                .title(docConfig.title)
//                .version(Meta.VERSION)
//                .description(docConfig.description)
//                .termsOfService("https://doc.xvoid.cn")
//                .license(License().name("Apache 2.0").url("https://doc.xvoid.cn"))
//            );
//    }
//
//    @Bean
//    open fun publicApi(): GroupedOpenApi = GroupedOpenApi.builder()
//        .group("all")
//        .pathsToMatch("/**")
//        .displayName("所有接口")
//        .build()
//
//    @Bean
//    open fun thirdPartyApi(): GroupedOpenApi = GroupedOpenApi.builder()
//        .group("third-party-api")
//        .pathsToMatch("/third-party/**")
//        .displayName("第三方接口")
//        .build()


}