package cn.toutatis.xvoid.spring.configure

import cn.toutatis.xvoid.spring.configure.system.VoidConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * @author Toutatis_Gc
 * VOID环境支持自动配置
 */
@Configuration
@EnableConfigurationProperties(VoidConfiguration::class)
open class VoidAutoConfiguration