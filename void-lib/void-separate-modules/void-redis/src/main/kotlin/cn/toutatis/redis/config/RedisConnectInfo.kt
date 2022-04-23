package cn.toutatis.redis.config

/**
 *@author Toutatis_Gc
 *@date 2022/4/16 14:53
 */
data class RedisConnectInfo(val username:String?,
                            val password:String?,
                            val address:String = "localhost",
                            val port:Int = 6379)
