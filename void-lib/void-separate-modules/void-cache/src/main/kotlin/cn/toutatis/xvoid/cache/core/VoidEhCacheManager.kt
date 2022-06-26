package cn.toutatis.xvoid.cache.core

import cn.toutatis.xvoid.toolkit.file.FileToolkit
import org.ehcache.Cache
import org.ehcache.CacheManager
import org.ehcache.PersistentCacheManager
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.ehcache.config.units.MemoryUnit
import java.io.File
import java.io.Serializable


/**
 * @author Toutatis_Gc
 * @date 2022/6/26 12:00
 *
 */
class VoidEhCacheManager {

    private val fileToolkit = FileToolkit.INSTANCE

    fun cla(): Unit {
        val runtimePath = fileToolkit.getRuntimePath(javaClass)
        val cacheManagerBuilder: CacheManagerBuilder<CacheManager> = CacheManagerBuilder.newCacheManagerBuilder()
        val persistentCacheManager: PersistentCacheManager = cacheManagerBuilder.with(CacheManagerBuilder.persistence(File(runtimePath, "VOID_PERSISTENT")))
            .withCache("preConfigured",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                    String::class.java,
                    Serializable::class.java,
                    ResourcePoolsBuilder
                        .heap(10)
                        .offheap(10, MemoryUnit.MB)
                        .disk(20, MemoryUnit.MB, true)
                ).build()
            ).build(true)


        val preConfigured: Cache<String, Serializable> = persistentCacheManager.getCache("preConfigured", String::class.java, Serializable::class.java)

//        preConfigured.put("1L", "da one!")
        val value = preConfigured.get("1L")
        System.err.println(value)
//        persistentCacheManager.removeCache("preConfigured")

        persistentCacheManager.close()

    }

}