package cn.toutatis.redis.client.inherit.jedis

import cn.toutatis.redis.client.VoidRedisClientInterface
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool

/**
 * @author Toutatis_Gc
 * @date 2022/4/29 18:58
 */
class VoidJedisPoolClient(private var jedisPool: JedisPool):VoidRedisClientInterface {

    private var jedis: Jedis
        get() {
            TODO()
        }
        set(value) {}

    override fun checkConnected(): Boolean {
        return true
    }

    override fun getDB(): Int {
        TODO("Not yet implemented")
    }

}