package cn.toutatis.xvoid.cache.support.spring

import cn.toutatis.xvoid.cache.core.VoidCache
import cn.toutatis.xvoid.cache.core.ehcache.VoidEhCacheManager
import cn.toutatis.xvoid.cache.support.CacheType
import cn.toutatis.xvoid.objects.ObjectToolkit
import cn.toutatis.xvoid.toolkit.file.FileToolkit
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File

/**
 * Spring缓存配置
 */
@Configuration
@EnableConfigurationProperties(VoidCacheConfiguration::class)
class VoidCacheAutoConfiguration {

    @Autowired
    private lateinit var voidCacheConfiguration: VoidCacheConfiguration

    private val fileToolkit = FileToolkit.INSTANCE

    private val objectToolkit = ObjectToolkit.getInstance()
    /**
     * 初始化缓存管理器
     */
    @Bean
    fun getVoidCache():VoidCache {
        when(voidCacheConfiguration.cacheType){
            CacheType.LOCAL -> {
                val path: String = voidCacheConfiguration.persistentPath ?: fileToolkit.getRuntimePath(javaClass)
                val voidEhCacheManager = VoidEhCacheManager()
                val cacheFileConfig = voidCacheConfiguration.cacheFileConfig
                if (!cacheFileConfig.configName.lowercase().endsWith(".xml")){
                    throw IllegalArgumentException("cacheFileConfig.configName must end with .xml")
                }
                var configPath = cacheFileConfig.configPath
                if (objectToolkit.strIsBlank(configPath)) {
                    configPath = fileToolkit.getRuntimePath(javaClass)
                }
                val file = File(configPath + "/" + cacheFileConfig.configName)
                if (file.exists()){
                    voidEhCacheManager.init(path,voidCacheConfiguration.persistentName,file.toURI().toURL())
                }else {
                    voidEhCacheManager.init(path,voidCacheConfiguration.persistentName,null)
                }
            }
            CacheType.REDIS -> {

            }
            else -> {

            }
        }

        val voidCache = VoidCache()
        return voidCache
    }

}