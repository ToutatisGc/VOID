package cn.toutatis.xvoid.support.spring.annotations

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 *@author Toutatis_Gc
 *@date 2022/4/15 14:28
 * SpringBoot启动类注解集合
 */
@EnableAsync
@EnableScheduling
@EnableConfigurationProperties
@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@EnableJpaRepositories
annotation class VoidApplication
