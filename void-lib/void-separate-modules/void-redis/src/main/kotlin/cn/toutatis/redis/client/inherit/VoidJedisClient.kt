package cn.toutatis.redis.client.inherit

import cn.toutatis.redis.client.VoidRedisClientInterface
import org.slf4j.LoggerFactory
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool

/**
 *@author Toutatis_Gc
 *@date 2022/4/19 13:55
 */
class VoidJedisClient : VoidRedisClientInterface {


    private val logger = LoggerFactory.getLogger(VoidJedisClient::class.java)

    private lateinit var jedisPool:JedisPool

    private var jedis:Jedis

    private val connectType:ConnectType

    constructor(jedisPool: JedisPool){
        this.jedisPool = jedisPool
        connectType = ConnectType.POOL
        jedis = this.jedisPool.resource
    }

    constructor(jedis:Jedis){
        connectType = ConnectType.SINGLE
        this.jedis = jedis
    }

    override fun isConnected():Boolean {
        if (jedis.isConnected){
            logger.info("Connect to redis server successfully, whoAmI: ${jedis.aclWhoAmI()}")
        }
        return jedis.isConnected
    }

//    fun isConnected(): Unit {
//        jedis
//    }

}