package cn.toutatis.xvoid.cache.support.spring

import cn.toutatis.xvoid.cache.core.VoidCache
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Spring缓存配置
 */
@Configuration
@EnableConfigurationProperties(VoidCacheConfiguration::class)
class VoidCacheAutoConfiguration {

    @Autowired
    private lateinit var voidCacheConfiguration: VoidCacheConfiguration

    /**
     * 初始化缓存管理器
     */
    @Bean
    fun getVoidCache():VoidCache{
        val voidCache = VoidCache()
        return voidCache
    }

}