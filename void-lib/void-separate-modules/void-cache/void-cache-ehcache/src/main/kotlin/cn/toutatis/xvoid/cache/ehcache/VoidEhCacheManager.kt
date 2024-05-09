package cn.toutatis.xvoid.cache.ehcache

import cn.toutatis.xvoid.cache.base.VoidCacheManager
import org.ehcache.config.builders.CacheManagerBuilder
import java.io.Serializable


class VoidEhCacheManager<in K: Serializable,in V: Serializable> : VoidCacheManager<K,V>{
    override fun getCacheManager(definition: String): Any {
        val cacheManagerBuilder = CacheManagerBuilder.newCacheManagerBuilder()

        cacheManagerBuilder.build(true)
        return cacheManagerBuilder.build(true)
    }

    override fun close() {
        TODO("Not yet implemented")
    }

    override fun <T> getValue(definition: String, key: K): T? {
        TODO("Not yet implemented")
    }

    override fun setValue(definition: String, key: K, value: V): Boolean {
        TODO("Not yet implemented")
    }
}