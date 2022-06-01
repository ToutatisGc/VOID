package cn.toutatis.redis.client.inherit.jedis

import cn.toutatis.redis.annotations.VoidRedisTransaction
import cn.toutatis.redis.client.VoidRedisClientInterface
import cn.toutatis.redis.exception.ConnectionException
import org.slf4j.LoggerFactory
import redis.clients.jedis.Jedis

/**
 *@author Toutatis_Gc
 *@date 2022/4/19 13:55
 */
open class VoidJedisClient(private val jedis: Jedis) : VoidRedisClientInterface {

    private val logger = LoggerFactory.getLogger(VoidJedisClient::class.java)

    init {
        checkConnected()
    }

    open override fun checkConnected():Boolean = jedis.isConnected.apply {
        if (this){
            logger.info("Connect to redis server successfully, whoAmI: ${jedis.aclWhoAmI()}")
        }else{
            throw ConnectionException("jedis connection failed.please check your configuration.")
        }
    }

    override fun getDB(): Int = jedis.db

    open fun tx(): Unit {
        val stackTrace = Thread.currentThread().stackTrace[4]
        val callMethodClass = Class.forName(stackTrace.className)
        val method = callMethodClass.getMethod(stackTrace.methodName)
        val annotation = method.getAnnotation(VoidRedisTransaction::class.java)
        System.err.println(7789)
    }
}