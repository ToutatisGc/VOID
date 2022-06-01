package cn.toutatis.redis.client

import org.slf4j.LoggerFactory

/**
 *@author Toutatis_Gc
 *@date 2022/4/16 15:08
 */
open class VoidRedisClient(private var redisClient: VoidRedisClientInterface) {

    private val logger = LoggerFactory.getLogger(VoidRedisClient::class.java)

    fun isConnected(): Boolean {
        return redisClient.checkConnected()
    }

    open fun test(): Unit {
        redisClient.getDB()
    }

}