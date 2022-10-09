package cn.toutatis.xvoid.cache.support.spring

import cn.toutatis.xvoid.PkgInfo
import cn.toutatis.xvoid.cache.core.VCache
import cn.toutatis.xvoid.cache.core.VoidCache
import cn.toutatis.xvoid.cache.core.ehcache.VoidEhCacheManager
import cn.toutatis.xvoid.cache.support.CacheType
import cn.toutatis.xvoid.toolkit.file.FileToolkit
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.validator.Validator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import java.io.FileNotFoundException

/**
 * Spring缓存配置
 */
@Configuration
@EnableConfigurationProperties(VoidCacheConfiguration::class)
class VoidCacheAutoConfiguration {

    private val logger = LoggerToolkit.getLogger(javaClass)

    @Autowired
    private lateinit var voidCacheConfiguration: VoidCacheConfiguration

    private val fileToolkit = FileToolkit


    /**
     * 初始化缓存管理器
     */
    @Bean
    fun getVoidCache(): VoidCache {
        val vCache: VCache
        logger.info("[${PkgInfo.MODULE_NAME}] 初始化缓存管理器,缓存类型:${voidCacheConfiguration.cacheType}")
        when (voidCacheConfiguration.cacheType) {
            CacheType.LOCAL -> {
                val path: String = voidCacheConfiguration.persistentPath ?: fileToolkit.getRuntimePath(javaClass)
                val voidEhCacheManager = VoidEhCacheManager()
                val cacheFileConfig = voidCacheConfiguration.cacheFileConfig
                if (!cacheFileConfig.configName.lowercase().endsWith(".xml")) {
                    throw IllegalArgumentException("cacheFileConfig.configName must end with .xml")
                }
                var configPath = cacheFileConfig.configPath
                if (Validator.strIsBlank(configPath)) {
                    configPath = fileToolkit.getRuntimePath(javaClass)
                }
                val file = File(configPath + "/" + cacheFileConfig.configName)
                logger.info("[${PkgInfo.MODULE_NAME}] 初始化缓存管理器,缓存配置文件路径:${file.path}")
                if (file.exists()) {
                    voidEhCacheManager.init(path, voidCacheConfiguration.persistentName, file.toURI().toURL())
                } else {
                    if(cacheFileConfig.useFileConfig){
                        logger.error("[${PkgInfo.MODULE_NAME}] 初始化缓存管理器,缓存配置文件不存在,请检查配置文件路径:${file.path}")
                        throw FileNotFoundException("${file.path} not found")
                    }
                    voidEhCacheManager.init(path, voidCacheConfiguration.persistentName,null)
                }
                vCache = voidEhCacheManager
            }
            CacheType.REDIS -> {
                TODO("not implemented")
            }
            else -> {
                throw IllegalArgumentException("cacheType must be LOCAL or REDIS")
            }
        }
        logger.info("[${PkgInfo.MODULE_NAME}] 初始化缓存管理器完成")
        return VoidCache(vCache)
    }

}