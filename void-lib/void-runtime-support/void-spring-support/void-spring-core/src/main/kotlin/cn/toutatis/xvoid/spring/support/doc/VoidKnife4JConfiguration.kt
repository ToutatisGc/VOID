package cn.toutatis.xvoid.spring.support.doc

import cn.toutatis.xvoid.common.Version
import cn.toutatis.xvoid.common.Meta
import cn.toutatis.xvoid.spring.configure.system.VoidConfiguration
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
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
    private lateinit var voidConfiguration : VoidConfiguration

    @Bean
    fun openAPI(): OpenAPI {
        val docConfig = voidConfiguration.docConfig
        return OpenAPI()
            .info(Info()
                .contact(Contact()
                    .name(Meta.ADMINISTRATOR)
                    .email("gc@toutatis.cn")
                    .url("https://github.com/ToutatisGc")
                )
                .title(docConfig.title)
                .version(Version.`$DEFAULT`.version)
                .description(docConfig.description)
                .termsOfService("https://doc.xvoid.cn")
                .license(License().name("Apache 2.0").url("https://doc.xvoid.cn"))
            );
    }

    @Bean
    fun publicApi(): GroupedOpenApi = GroupedOpenApi.builder()
        .group("all")
        .pathsToMatch("/**")
        .displayName("所有接口")
        .build()

    @Bean
    fun thirdPartyApi(): GroupedOpenApi = GroupedOpenApi.builder()
        .group("third-party-api")
        .pathsToMatch("/third-party/**")
        .displayName("第三方接口")
        .build()


}