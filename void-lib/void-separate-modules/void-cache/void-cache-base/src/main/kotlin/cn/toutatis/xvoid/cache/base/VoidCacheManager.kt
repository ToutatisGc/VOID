package cn.toutatis.xvoid.cache.base

import java.io.Serializable

interface VoidCacheManager<in K:Serializable,in V:Serializable> {

    fun getCacheManager(definition: String): Any

    fun setValue(definition:String, key: K, value: V): Boolean

    fun <T> getValue(definition: String,key:K): T?

    fun close()


}