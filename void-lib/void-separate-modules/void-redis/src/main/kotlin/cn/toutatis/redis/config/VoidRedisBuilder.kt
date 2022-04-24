package cn.toutatis.redis.config

import cn.toutatis.redis.client.VoidRedisClient
import cn.toutatis.redis.client.inherit.VoidJedisClient
import cn.toutatis.toolkit.file.FileToolkit
import cn.toutatis.toolkit.json.getItValue
import com.alibaba.fastjson.JSONObject
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import java.io.File
import java.io.FileNotFoundException

/**
 *@author Toutatis_Gc
 *@date 2022/4/16 12:40
 * Redis客户端构建
 */
class VoidRedisBuilder{

    /*默认使用资源下的配置文件*/
    private var configPath = "void-redis.json"

    private val fileToolkit = FileToolkit.INSTANCE;

    private var clientType = ClientType.LETTUCE;

    private var username: String? = null

    private var password: String? = null

    private var address: String = "localhost"

    private var port: Int =  6379

    private var usePool : Boolean = true

    private lateinit var config : JSONObject


    constructor()

    constructor(redisConnectInfo: RedisConnectInfo){
        this.username = redisConnectInfo.username
        this.password = redisConnectInfo.password
        this.address = redisConnectInfo.address
        this.port = redisConnectInfo.port
    }

    fun setUsername(username:String): VoidRedisBuilder {
        this.username = username
        return this
    }

    fun setClientType(clientType: ClientType): VoidRedisBuilder {
        this.clientType = clientType
        return this
    }

    fun setUsePool(usePool:Boolean): VoidRedisBuilder {
        this.usePool = usePool
        return this
    }

    fun setConfig(): VoidRedisBuilder {
        val config = fileToolkit.getResourcesFile(configPath)
            ?: throw FileNotFoundException("void-redis.json配置文件找不到,请配置配置文件或指定配置文件.")
        this.config = JSONObject.parseObject(fileToolkit.getFileContent(File(config.toURI())))
        return this
    }


    fun setConfig(configPath:String): VoidRedisBuilder {
        this.configPath = configPath
        setConfig()
        return this
    }

    fun buildClient(): VoidRedisClient {
        val timeOut = config.getItValue("general.time-out", String::class.java)
        System.err.println(timeOut)
        when(clientType){
            /*连接Jedis*/
            ClientType.JEDIS ->{
                if (usePool){
                    /*TODO 配置文件填充，暂时使用默认值*/
                    val jedisPoolConfig = JedisPoolConfig()
                    jedisPoolConfig.testWhileIdle = true
                    val jedisPool:JedisPool =
                        if (username != null && password != null){
                            JedisPool(jedisPoolConfig,address,port,username,password)
                        }else if (password != null){
                            JedisPool(jedisPoolConfig,address,port,0,password)
                        }else{
                            JedisPool(jedisPoolConfig,address,port)
                        }
                    return VoidRedisClient(VoidJedisClient(jedisPool))
                }else{
                    val jedis = Jedis(address,port)
                    if (username != null && password != null){
                        jedis.auth(username,password)
                    }else if(password != null){
                        jedis.auth(password)
                    }
                    val connected = jedis.client.isConnected
                }
            }
            ClientType.LETTUCE ->{

            }
        }
        /*TODO 未完成*/
        return VoidRedisClient(VoidJedisClient(JedisPool()))
    }
}