package cn.toutatis.redis

import cn.toutatis.redis.config.ClientType
import cn.toutatis.redis.config.RedisConnectInfo
import cn.toutatis.redis.config.VoidRedisBuilder
import org.junit.Assert
import org.junit.Test

/**
 *@author Toutatis_Gc
 *@date 2022/4/16 12:48
 */
class ClientTest {

    @Test
    fun testGetClient() {
        val client = VoidRedisBuilder(RedisConnectInfo("gwg@w0d0t", host = "192.168.154.200"))
            .setClientType(ClientType.JEDIS)
            .buildClient()
        Assert.assertEquals("Jedis单连接测试",true,client.isConnected())
        val client1 = VoidRedisBuilder(RedisConnectInfo("Gc", "gwg@w0d0t1", host = "192.168.154.200"))
            .setClientType(ClientType.JEDIS)
            .buildClient()
        Assert.assertEquals("Jedis连接池测试",true,client1.isConnected())
    }

}