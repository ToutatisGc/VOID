package cn.toutatis.redis.client

/**
 *@author Toutatis_Gc
 *@date 2022/4/19 13:56
 */
interface VoidRedisClientInterface {

    fun checkConnected():Boolean

    fun getDB(): Int

}