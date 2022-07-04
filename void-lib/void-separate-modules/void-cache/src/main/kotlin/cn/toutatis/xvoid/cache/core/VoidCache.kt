package cn.toutatis.xvoid.cache.core

import org.ehcache.Cache
import java.io.Serializable

/**
 * @author Toutatis_Gc
 * @date 2022/6/26 14:34
 *
 */
class VoidCache constructor(private val vc: VCache):VCache {

    override fun getCache(definition: String): Cache<Serializable, Serializable>? {
        vc.getCache(definition)?.let {
            return it as Cache<Serializable, Serializable>
        }
        return null
    }

    override fun setValue(definition: String, key: Serializable, value: Serializable): Boolean {
        return vc.setValue(definition, key, value)
    }

    override fun <T> getValue(definition: String, key: String): T? {
        return vc.getValue(definition, key)
    }

    override fun close() {
        vc.close()
    }

}