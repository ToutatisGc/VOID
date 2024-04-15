package cn.toutatis.xvoid.cache.base

import java.io.Serializable

interface VoidCacheManager {

    fun getCacheManager(definition: String): VoidCacheManager

    fun setValue(definition:String, key: Serializable, value: Serializable): Boolean

    fun <T> getValue(definition: String,key:String): T?

    fun close()


}