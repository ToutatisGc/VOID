package cn.toutatis.redis.client.inherit

import cn.toutatis.redis.client.VoidRedisClientInterface
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool

/**
 *@author Toutatis_Gc
 *@date 2022/4/19 13:55
 */
class VoidJedisClient : VoidRedisClientInterface {

    constructor(jedisPool: JedisPool){

    }

    constructor(jedis:Jedis){

    }

//    fun isConnected(): Unit {
//        jedis
//    }

}