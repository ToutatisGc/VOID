package cn.toutatis.spring.config.orm.mybatisplus

import com.baomidou.mybatisplus.annotation.DbType
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder


@Configuration
class MybatisPlusConfiguration {

    @Bean
    fun mybatisPlusInterceptor(): MybatisPlusInterceptor {
        val mybatisPlusInterceptor = MybatisPlusInterceptor()
        /*PaginationInnerInterceptor 分页插件*/
        mybatisPlusInterceptor.addInnerInterceptor(PaginationInnerInterceptor(DbType.MYSQL))
        /*OptimisticLockerInnerInterceptor 乐观锁插件*/
        mybatisPlusInterceptor.addInnerInterceptor(OptimisticLockerInnerInterceptor())
        return mybatisPlusInterceptor
    }

//    @Bean
//    fun customizer(): Jackson2ObjectMapperBuilderCustomizer {
//        return Jackson2ObjectMapperBuilderCustomizer { builder: Jackson2ObjectMapperBuilder ->
//            builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
//        }
//    }
}