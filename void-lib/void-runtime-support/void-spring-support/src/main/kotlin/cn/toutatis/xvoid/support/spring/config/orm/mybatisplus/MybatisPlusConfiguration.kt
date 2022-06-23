package cn.toutatis.xvoid.support.spring.config.orm.mybatisplus

import com.baomidou.mybatisplus.annotation.DbType
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Toutatis_Gc
 * https://baomidou.com/pages/2976a3/#mybatisplusinterceptor
 * MybatisPlus插件配置
 */
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

}