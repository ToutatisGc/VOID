package cn.toutatis.redis.client.inherit

import cn.toutatis.redis.client.VoidRedisClientInterface
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool

/**
 *@author Toutatis_Gc
 *@date 2022/4/19 13:55
 */
class VoidJedisClient : VoidRedisClientInterface {

    private lateinit var jedisPool:JedisPool

    private var jedis:Jedis

    constructor(jedisPool: JedisPool){
        this.jedisPool = jedisPool
        jedis = this.jedisPool.resource
    }

    constructor(jedis:Jedis){
        this.jedis = jedis
    }

    override fun isConnected():Boolean {
        return jedis.isConnected
    }

//    fun isConnected(): Unit {
//        jedis
//    }

}