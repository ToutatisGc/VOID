package cn.toutatis.redis

import cn.toutatis.redis.config.ClientType
import cn.toutatis.redis.config.RedisConnectInfo
import cn.toutatis.redis.config.VoidRedisBuilder
import org.junit.jupiter.api.Test

/**
 *@author Toutatis_Gc
 *@date 2022/4/16 12:48
 */
class ClientTest {

    @Test
    fun testGetClient() {
        val client = VoidRedisBuilder(RedisConnectInfo("Gc", "gwg@w0d0t1", address = "192.168.154.200"))
            .setClientType(ClientType.JEDIS)
            .buildClient()
        val connected = client.isConnected()
        System.err.println(connected)
    }

}