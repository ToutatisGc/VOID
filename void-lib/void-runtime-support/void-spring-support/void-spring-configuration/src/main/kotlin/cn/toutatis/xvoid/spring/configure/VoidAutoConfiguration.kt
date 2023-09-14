package cn.toutatis.xvoid.spring.configure

import cn.toutatis.xvoid.spring.configure.system.VoidDeliveryConfiguration
import cn.toutatis.xvoid.spring.configure.system.VoidSecurityConfiguration
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * @author Toutatis_Gc
 * VOID环境支持自动配置
 */
@Configuration
@EnableConfigurationProperties(
    VoidGlobalConfiguration::class,
    VoidSecurityConfiguration::class,
    VoidDeliveryConfiguration::class
)
open class VoidAutoConfiguration