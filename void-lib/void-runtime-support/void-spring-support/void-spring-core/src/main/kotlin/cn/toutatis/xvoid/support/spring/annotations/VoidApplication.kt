package cn.toutatis.xvoid.support.spring.annotations

import cn.toutatis.xvoid.support.VoidModuleInfo
import cn.toutatis.xvoid.support.VoidModuleInfo.*
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.ComponentScan
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
@ComponentScan(basePackages = [BASE_PACKAGE])
@EntityScan(basePackages=[BASE_PACKAGE])
@MapperScan("$BASE_PACKAGE.**.persistence")
@EnableJpaRepositories(basePackages = [BASE_PACKAGE])
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
annotation class VoidApplication
