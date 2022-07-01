package cn.toutatis.xvoid.cache.core.ehcache

import cn.toutatis.xvoid.cache.core.VCache
import cn.toutatis.xvoid.cache.core.VoidCommonCacheDefinition
import org.ehcache.Cache
import org.ehcache.CacheManager
import org.ehcache.PersistentCacheManager
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ExpiryPolicyBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.ehcache.config.units.EntryUnit
import org.ehcache.config.units.MemoryUnit
import org.ehcache.expiry.ExpiryPolicy
import org.ehcache.xml.XmlConfiguration
import java.io.File
import java.io.Serializable
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
class VoidEhCacheManager : VCache{

    /**
     * 持久化缓存管理器
     */
    private lateinit var persistentCacheManager : PersistentCacheManager

    /**
     * 内存缓存管理器
     */
    private lateinit var defaultCacheManager : CacheManager

    /**
     * 缓存初始化
     */
    fun init(path:String, fileName:String,configFile: URL?) {

//        val transactionManager = TransactionManagerServices.getTransactionManager()

        val cacheManagerBuilder: CacheManagerBuilder<CacheManager> = CacheManagerBuilder.newCacheManagerBuilder()

        val persistentCacheManagerBuilder: CacheManagerBuilder<PersistentCacheManager> = cacheManagerBuilder.with(
            CacheManagerBuilder.persistence(File(path, fileName))
        )

        if (configFile != null) {
            /*TODO 看怎么可以把xml配置合并成一个cacheManager*/
            defaultCacheManager =  CacheManagerBuilder.newCacheManager(XmlConfiguration(configFile))
            defaultCacheManager.init()
        }else{
            defaultCacheManager = cacheManagerBuilder.build(true)
        }

        persistentCacheManager = persistentCacheManagerBuilder.build(true)

        for (cacheDefinition in VoidCommonCacheDefinition.values()) {
            val resourcePoolsBuilder = ResourcePoolsBuilder.newResourcePoolsBuilder()
                .heap(200, EntryUnit.ENTRIES)
                .offheap(1, MemoryUnit.MB)
                .disk(20, MemoryUnit.MB, true)
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

            when(cacheDefinition.persistent){
                true -> persistentCacheManager.createCache(cacheDefinition.cacheName,cacheConfiguration)
                false -> defaultCacheManager.createCache(cacheDefinition.cacheName,cacheConfiguration)
            }
        }

    }


    /**
     * 获取缓存
     * @param definition 缓存定义
     * @return 缓存值
     * 当缓存不存在时，会报错缓存不存在
     */
    fun <T> getValue(definition: VoidCommonCacheDefinition, key: String): T? {
        val cache = getCache(definition)
        return cache.get(key) as T?
    }

    fun getCache(definition:VoidCommonCacheDefinition): Cache<in Serializable, in Serializable> {
        /*ISSUE kotlin的型变和Java的对应不上?*/
        val keyClazz = definition.keyClazz as Class<Serializable>
        val valueClazz = definition.valueClazz as Class<Serializable>
        val cache : Cache<Serializable,Serializable> = when(definition.persistent){
            true -> persistentCacheManager.getCache(definition.cacheName,keyClazz, valueClazz)
            false -> defaultCacheManager.getCache(definition.cacheName,keyClazz, valueClazz)
        }
        return cache
    }

    fun putValue(definition: VoidCommonCacheDefinition,key: Serializable,value:Serializable) {
        val cache = getCache(definition)
        cache.put(key,value)
    }



    override fun getValue() {
        TODO("Not yet implemented")
    }

    fun close(): Unit {
        persistentCacheManager.close()
        defaultCacheManager.close()
    }

    fun getCache(definition: String) : Cache<String,String> {
       return persistentCacheManager.getCache(definition,String::class.java,String::class.java)
    }

}