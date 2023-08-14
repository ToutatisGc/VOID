package cn.toutatis.redis

import cn.toutatis.redis.config.ClientType
import cn.toutatis.redis.config.RedisConnectInfo
import cn.toutatis.redis.config.VoidRedisBuilder
import org.junit.Assert
import org.junit.Test
import org.slf4j.LoggerFactory

/**
 *@author Toutatis_Gc
 *@date 2022/4/16 12:48
 */
class ClientTest {

    private val logger = LoggerFactory.getLogger(ClientTest::class.java)

    val host = "192.168.154.200"

//    @Test
    fun testGetClient() {
        //连接用的是虚拟机连接，修改成自己的IP
        val client = VoidRedisBuilder(RedisConnectInfo("gwg@w0d0t", host = host))
            .setClientType(ClientType.JEDIS)
            .setUsePool(false)
            .buildClient()
        Assert.assertEquals("Jedis单连接测试",true,client.isConnected())
        client.test()
        logger.info("Jedis单连接测试连接成功")
        val client1 = VoidRedisBuilder(RedisConnectInfo("Gc", "gwg@w0d0t1", host))
            .setClientType(ClientType.JEDIS)
            .buildClient()
        Assert.assertEquals("Jedis连接池测试",true,client1.isConnected())
        logger.info("Jedis连接池测试连接成功")
    }

}