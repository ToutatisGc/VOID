package cn.toutatis.xvoid.spring.support.doc

import cn.toutatis.xvoid.spring.configure.system.VoidConfiguration
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
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
        return OpenAPI()
            .info(Info()
                .title("XXX用户系统API")
                .version("1.0")
                .description("Knife4j集成springdoc-openapi示例")
                .termsOfService("http://doc.xiaominfo.com")
                .license(License().name("Apache 2.0")
                    .url("http://doc.xiaominfo.com"))
            );
    }


}