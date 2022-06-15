package cn.toutatis.support.spring.annotations

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

/**
 *@author Toutatis_Gc
 *@date 2022/4/15 14:28
 */

@EnableAsync
@EnableScheduling
@EnableConfigurationProperties
@ServletComponentScan
@SpringBootApplication
annotation class VoidApplication
