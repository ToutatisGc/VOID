package cn.toutatis.xvoid.cache.ehcache

import cn.toutatis.xvoid.cache.base.Meta
import cn.toutatis.xvoid.cache.base.VoidCache
import cn.toutatis.xvoid.cache.base.VoidCacheDefinition
import cn.toutatis.xvoid.cache.base.VoidCacheManager
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.infoWithModule
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
class VoidEhCacheManagerT<in K: Serializable,in V: Serializable> : VoidCacheManager<K,V>{

    private val logger  = LoggerToolkit.getLogger(javaClass)


    constructor(path: String)

    private constructor(){
        logger.infoWithModule(Meta.MODULE_NAME,"EhCacheManager已初始化")
        VoidCache.init(this)
    }

    /**
     * 持久化缓存管理器
     */
    private lateinit var persistentCacheManager : PersistentCacheManager

    /**
     * 内存缓存管理器
     */
    private lateinit var defaultCacheManager : CacheManager

    /**
     * 初始化缓存管理器
     * @param storePath 持久化缓存路径
     * @param configFile 配置文件
     */
    fun init(storePath:String,configFile: URL?) {

//        val transactionManager = TransactionManagerServices.getTransactionManager()

        val cacheManagerBuilder: CacheManagerBuilder<CacheManager> = CacheManagerBuilder.newCacheManagerBuilder()

        val persistentCacheManagerBuilder: CacheManagerBuilder<PersistentCacheManager> = cacheManagerBuilder.with(
            CacheManagerBuilder.persistence(File(storePath))
        )

        if (configFile != null) {
            defaultCacheManager =  CacheManagerBuilder.newCacheManager(XmlConfiguration(configFile))
            defaultCacheManager.init()
        }else{
            defaultCacheManager = cacheManagerBuilder.build(true)
        }

        persistentCacheManager = persistentCacheManagerBuilder.build(true)

        for (cacheDefinition in VoidCacheDefinition.entries) {
            val resourcePoolsBuilder = ResourcePoolsBuilder.newResourcePoolsBuilder()
                .heap(200, EntryUnit.ENTRIES)
                .offheap(1, MemoryUnit.MB)
                .disk(20, MemoryUnit.MB, cacheDefinition.persistent)
            val cacheConfiguration = CacheConfigurationBuilder.newCacheConfigurationBuilder(
                cacheDefinition.keyClazz, cacheDefinition.valueClazz, resourcePoolsBuilder
            )
            when(cacheDefinition.expiredPolicy){
                VoidCacheDefinition.DataExpiredPolicy.NO_EXPIRED -> {
                    cacheConfiguration.withExpiry(ExpiryPolicy.NO_EXPIRY)
                }
                VoidCacheDefinition.DataExpiredPolicy.EXPIRED_CREATED -> {
                    cacheConfiguration.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(
                        Duration.ofSeconds(cacheDefinition.expiredTime)
                    ))
                }
                VoidCacheDefinition.DataExpiredPolicy.EXPIRE_IDLE -> {
                    cacheConfiguration.withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(
                        Duration.ofSeconds(cacheDefinition.expiredTime)
                    ))
                }
                else ->{
                    throw IllegalArgumentException("不支持的缓存过期策略")
                }
            }

            when(cacheDefinition.persistent){
                true -> persistentCacheManager.createCache(cacheDefinition.cacheDefinition,cacheConfiguration)
                false -> defaultCacheManager.createCache(cacheDefinition.cacheDefinition,cacheConfiguration)
            }
        }

    }

    @Suppress("UNCHECKED_CAST")
    fun getCache(definition:VoidCacheDefinition): Cache<in Serializable, in Serializable> {
        /*ISSUE kotlin的型变和Java的对应不上?*/
        val keyClazz = definition.keyClazz as Class< Serializable>
        val valueClazz = definition.valueClazz as Class<Serializable>
        val cache : Cache<Serializable,Serializable> = when(definition.persistent){
            true -> persistentCacheManager.getCache(definition.cacheDefinition,keyClazz, valueClazz)
            false -> defaultCacheManager.getCache(definition.cacheDefinition,keyClazz, valueClazz)
        }
        return cache
    }

    @Suppress("UNCHECKED_CAST")
    fun getCache(definition: String) : Cache<Serializable,Serializable>? {
        VoidCacheDefinition.entries.forEach {
            if(it.cacheDefinition == definition){
                return when(it.persistent){
                    true -> persistentCacheManager.getCache(definition,it.keyClazz as Class<Serializable>,it.valueClazz as Class<Serializable>)
                    false -> defaultCacheManager.getCache(definition,it.keyClazz as Class<Serializable>,it.valueClazz as Class<Serializable>)
                }
            }
        }
        return null
    }

    override fun getCacheManager(definition: String): Any {
        return this
    }

    override fun <T> getValue(definition: String, key: K): T? {
        val cache = getCache(definition)
        return cache?.get(key) as T?
    }

    override fun setValue(definition: String, key: K, value: V): Boolean {
        val cache = getCache(definition)
        val success:Boolean = if(cache != null){
            cache.put(key,value)
            true
        }else{
            logger.error("[${Meta.MODULE_NAME}]缓存不存在,缓存名称:$definition")
            false
        }
        return success
    }

    override fun close(): Unit {
        persistentCacheManager.close()
        defaultCacheManager.close()
    }


}