package cn.toutatis.xvoid.spring.support.doc

import cn.toutatis.xvoid.common.Meta
import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemResource
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.media.Schema
import org.springdoc.core.GroupedOpenApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc


/**
 * Void knife4j configuration
 *
 * @constructor Create empty Void knife4j configuration
 */
@Configuration
@EnableWebMvc
class VoidKnife4JConfiguration {

    @Autowired
    private lateinit var voidGlobalConfiguration : VoidGlobalConfiguration

    @Bean
    open fun openAPI(): OpenAPI {
        val docConfig = voidGlobalConfiguration.docConfig
        val info = OpenAPI()
            .info(Info()
                .contact(Contact()
                    .name(Meta.ADMINISTRATOR)
                    .email("gc@toutatis.cn")
                    .url("https://github.com/ToutatisGc")
                )
                .title(docConfig.title)
                .version(Meta.VERSION)
                .description(docConfig.description)
                .termsOfService("https://doc.xvoid.cn")
                .license(License().name("Apache 2.0").url("https://doc.xvoid.cn"))
            )
        val components = Components()
        components.addSchemas("测试资源", Schema<SystemResource>())
        info.components(components)
        return info
    }

    @Bean
    open fun publicApi(): GroupedOpenApi = GroupedOpenApi.builder()
        .group("all")
        .pathsToMatch("/**")
        .displayName("接口列表")
        .build()

    @Bean
    open fun thirdPartyApi(): GroupedOpenApi = GroupedOpenApi.builder()
        .group("third-party-api")
        .pathsToMatch("/third-party/**")
        .displayName("第三方接口")
        .build()


}