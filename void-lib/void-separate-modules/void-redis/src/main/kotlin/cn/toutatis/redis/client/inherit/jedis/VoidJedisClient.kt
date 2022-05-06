package cn.toutatis.redis.client.inherit.jedis

import cn.toutatis.redis.client.VoidRedisClientInterface
import cn.toutatis.redis.client.inherit.ConnectType
import cn.toutatis.redis.exception.ConnectionException
import org.slf4j.LoggerFactory
import redis.clients.jedis.Jedis

/**
 *@author Toutatis_Gc
 *@date 2022/4/19 13:55
 */
class VoidJedisClient(private val jedis: Jedis) : VoidRedisClientInterface {


    private val logger = LoggerFactory.getLogger(VoidJedisClient::class.java)

    private val connectType: ConnectType = ConnectType.SINGLE

    init {
        checkConnected()
    }

    override fun checkConnected():Boolean = jedis.isConnected.apply {
        if (this){
            logger.info("Connect to redis server successfully, whoAmI: ${jedis.aclWhoAmI()}")
        }else{
            throw ConnectionException("jedis connection failed.please check your configuration.")
        }
    }

}