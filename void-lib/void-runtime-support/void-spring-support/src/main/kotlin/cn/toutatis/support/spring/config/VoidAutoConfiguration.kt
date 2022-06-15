package cn.toutatis.support.spring.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(VoidConfiguration::class)
class VoidAutoConfiguration 