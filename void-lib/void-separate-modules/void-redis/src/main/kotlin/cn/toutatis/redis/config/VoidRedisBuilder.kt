package cn.toutatis.redis.config

import cn.toutatis.redis.client.VoidRedisClient
import cn.toutatis.redis.client.inherit.VoidJedisClient
import cn.toutatis.toolkit.file.FileToolkit
import cn.toutatis.toolkit.json.getItBoolean
import com.alibaba.fastjson.JSONObject
import org.slf4j.LoggerFactory
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


    private val logger = LoggerFactory.getLogger(VoidRedisBuilder::class.java)

    /**
     * 默认使用资源下的配置文件
     */
    private var configPath = "void-redis.json"

    private val fileToolkit = FileToolkit.INSTANCE

    /**
     * 默认使用Lettuce作为客户端
     */
    private var clientType = ClientType.LETTUCE

    private var username: String? = null

    private var password: String? = null

    private var address: String = "localhost"

    private var port: Int =  6379

    private var usePool : Boolean = true

    private lateinit var config : JSONObject

    /**
     * The configured priority is hard code > Profile
     */
    constructor(){
        setConfig()
    }

    /**
     * The configured priority is hard code > Profile
     */
    constructor(redisConnectInfo: RedisConnectInfo){
        setConfig()
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
        //根文件必须指定,不得缺失
        val rootConfigFile = fileToolkit.getResourcesFile(configPath)
            ?: throw FileNotFoundException("[void-redis.json] configuration file could not be found. Please configure the file or specify the configuration file.")
        val generalConfig = JSONObject.parseObject(fileToolkit.getFileContent(File(rootConfigFile.toURI())))
        val includeFiles = generalConfig.getJSONArray("include")
        if (includeFiles!= null && includeFiles.size > 0){
            for (filename in includeFiles) {
                filename as String
                val includeFile = fileToolkit.getResourcesFile(filename)
                if (includeFile != null){
                    val includeConfigJSONObject = JSONObject.parseObject(fileToolkit.getFileContent(File(includeFile.toURI())))
                    generalConfig.putAll(includeConfigJSONObject)
                }else{
                    logger.warn("Configuration file $filename not exist , ignore this file.")
                }
            }
            generalConfig.remove("include")
        }
        this.config = generalConfig
        return this
    }


    /**
     * Specify the configuration file path.
     */
    fun setConfig(configPath:String): VoidRedisBuilder {
        this.configPath = configPath
        setConfig()
        return this
    }

    fun buildClient(): VoidRedisClient {
        when(clientType){
            /*连接Jedis*/
            ClientType.JEDIS ->{
                if (usePool){
                    val jedisPoolConfig = JedisPoolConfig()
                    with(config){
                        /*TODO 配置文件填充，暂时使用默认值*/
                        jedisPoolConfig.testWhileIdle = getItBoolean("general.time-out",true)
                    }
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