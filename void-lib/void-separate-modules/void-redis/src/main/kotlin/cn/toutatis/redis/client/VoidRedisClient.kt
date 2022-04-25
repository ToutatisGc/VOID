package cn.toutatis.redis.client

import org.slf4j.LoggerFactory

/**
 *@author Toutatis_Gc
 *@date 2022/4/16 15:08
 */
class VoidRedisClient(private var redisClient: VoidRedisClientInterface) {

    private val logger = LoggerFactory.getLogger(VoidRedisClient::class.java)

    fun isConnected(): Boolean {
        return redisClient.isConnected()
    }

}