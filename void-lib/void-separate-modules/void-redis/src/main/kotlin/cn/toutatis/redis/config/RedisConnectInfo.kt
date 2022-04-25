package cn.toutatis.redis.config

import redis.clients.jedis.Protocol

/**
 *@author Toutatis_Gc
 *@date 2022/4/16 14:53
 */
class RedisConnectInfo {

    var username: String? = null
    val password: String
    val host: String
    var port: Int = Protocol.DEFAULT_PORT

    constructor(password: String,host: String){
        this.password = password
        this.host = host
    }

    constructor(username: String?, password: String, host: String = Protocol.DEFAULT_HOST, port: Int = Protocol.DEFAULT_PORT) {
        this.username = username
        this.password = password
        this.host = host
        this.port = port
    }
}
