package cn.toutatis.xvoid.cache.core.ehcache

import cn.toutatis.xvoid.cache.core.VoidCommonCacheDefinition
import org.ehcache.CacheManager
import org.ehcache.PersistentCacheManager
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ExpiryPolicyBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.ehcache.expiry.ExpiryPolicy
import org.ehcache.xml.XmlConfiguration
import java.io.File
import java.net.URL
import java.time.Duration


/**
 * @author Toutatis_Gc
 * @date 2022/6/26 12:00
 * @description ehcache缓存管理器
 *
 * 常被查询、最重要、数据量较小的数据存放在堆缓存，不用担心JVM的重启，有持久化机制；
 * 常被查询、数据量中等的数据存放在堆外缓存，几个G就好了，不用担心服务器的重启，有持久化机制；
 * 不常用、大量的数据、但又不想占用数据库IO的数据，放在Disk缓存，容量自便；
 */
class VoidEhCacheManager {

    /**
     * 持久化缓存管理器
     */
    private lateinit var persistentCacheManager : PersistentCacheManager

    /**
     * 内存缓存管理器
     */
    private lateinit var defaultCacheManager : CacheManager

    fun init(path:String, fileName:String,configFile: URL?): Unit {

        val cacheManagerBuilder: CacheManagerBuilder<CacheManager> = CacheManagerBuilder.newCacheManagerBuilder()

        if (configFile != null) {
            defaultCacheManager = CacheManagerBuilder.newCacheManager(XmlConfiguration(configFile))
            defaultCacheManager.init()
        }else{
            defaultCacheManager = cacheManagerBuilder.build(true)
        }

        persistentCacheManager = cacheManagerBuilder.with(
            CacheManagerBuilder.persistence(File(path, fileName))
        ).build(true)

        for (cacheDefinition in VoidCommonCacheDefinition.values()) {
            val resourcePoolsBuilder = ResourcePoolsBuilder.heap(10)
            val cacheConfiguration = CacheConfigurationBuilder.newCacheConfigurationBuilder(
                cacheDefinition.keyClazz, cacheDefinition.valueClazz, resourcePoolsBuilder
            )
            when(cacheDefinition.expiredPolicy){
                VoidCommonCacheDefinition.DataExpiredPolicy.NO_EXPIRED -> {
                    cacheConfiguration.withExpiry(ExpiryPolicy.NO_EXPIRY)
                }
                VoidCommonCacheDefinition.DataExpiredPolicy.EXPIRED_CREATED -> {
                    cacheConfiguration.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(
                        Duration.ofSeconds(cacheDefinition.expiredTime)
                    ))
                }
                VoidCommonCacheDefinition.DataExpiredPolicy.EXPIRE_IDLE -> {
                    cacheConfiguration.withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(
                        Duration.ofSeconds(cacheDefinition.expiredTime)
                    ))
                }
                else ->{
                    throw IllegalArgumentException("不支持的缓存过期策略")
                }
            }
            cacheConfiguration.build()
            when(cacheDefinition.persistent){
                true -> persistentCacheManager.createCache(cacheDefinition.cacheName,cacheConfiguration)
                false -> defaultCacheManager.createCache(cacheDefinition.cacheName,cacheConfiguration)
            }

        }

    }

}