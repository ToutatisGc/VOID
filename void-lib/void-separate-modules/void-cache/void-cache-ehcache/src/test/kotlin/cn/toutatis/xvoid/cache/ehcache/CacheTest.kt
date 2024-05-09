package cn.toutatis.xvoid.cache.ehcache

import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.ehcache.config.units.EntryUnit
import org.ehcache.config.units.MemoryUnit
import org.junit.Test

class CacheTest {

    @Test
    fun test1(){
        val cacheManagerBuilder = CacheManagerBuilder.newCacheManagerBuilder()
        val cacheManager = cacheManagerBuilder.build(true)

        val cache = cacheManager.getCache("test", String::class.java, String::class.java)
        System.err.println(cache)
        val disk = ResourcePoolsBuilder.newResourcePoolsBuilder()
            .heap(4096, EntryUnit.ENTRIES)
            .offheap(256, MemoryUnit.MB)
        val build = disk.build()
        cacheManager.createCache("test", CacheConfigurationBuilder.newCacheConfigurationBuilder(
            String::class.java, Integer::class.java, build
        ))
        try {
            cacheManager.getCache("test", String::class.java, String::class.java)
        }catch (e:Exception){
            e.printStackTrace()
        }
        cacheManager.close()

    }
}