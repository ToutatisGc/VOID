package cn.toutatis.xvoid.cache.core

import java.io.Serializable

interface VCache {

    fun getCache(definition: String): Any?

    fun setValue(definition:String,key: Serializable, value: Serializable): Boolean

    fun <T> getValue(definition: String,key:String): T?

    fun close()

}