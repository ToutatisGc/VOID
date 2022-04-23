package cn.toutatis.redis.client

/**
 *@author Toutatis_Gc
 *@date 2022/4/16 15:08
 */
class VoidRedisClient {

    private lateinit var redisClient : VoidRedisClientInterface

    constructor(redisClient:VoidRedisClientInterface){
        this.redisClient = redisClient
    }

    private fun destroy(): Unit {
        System.err.println(777)
    }

}