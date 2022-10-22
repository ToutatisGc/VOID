package cn.toutatis.redis.config

import cn.toutatis.redis.client.VoidRedisClient
import cn.toutatis.redis.client.inherit.jedis.VoidJedisClient
import cn.toutatis.redis.client.inherit.jedis.VoidJedisPoolClient
import cn.toutatis.xvoid.toolkit.file.FileToolkit
import cn.toutatis.xvoid.toolkit.formatting.getItBoolean
import cn.toutatis.xvoid.toolkit.formatting.getItInteger
import com.alibaba.fastjson.JSONObject
import org.slf4j.LoggerFactory
import redis.clients.jedis.*
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

    private val fileToolkit = FileToolkit

    /**
     * 默认使用Lettuce作为客户端
     */
    private var clientType = ClientType.LETTUCE

    private var username: String? = null

    private lateinit var password: String

    private var host: String = "localhost"

    private var port: Int =  6379

    private var usePool : Boolean = true

    private var enableTransaction = false

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
        this.host = redisConnectInfo.host
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

    @Deprecated("暂时全部使用连接池")
    fun setUsePool(usePool:Boolean): VoidRedisBuilder {
        this.usePool = usePool
        return this
    }

    fun setEnableTransaction(enableTransaction:Boolean): VoidRedisBuilder {
        this.enableTransaction = enableTransaction
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
                val pPassword = this.password
                val jedisClientConfig:JedisClientConfig = object : JedisClientConfig {
                    override fun getUser(): String? = config.getString("username") ?: username
                    override fun getPassword(): String = config.getString("password") ?: pPassword
                    override fun getDatabase(): Int = config.getItInteger("general.database", Protocol.DEFAULT_DATABASE)
                    override fun getConnectionTimeoutMillis(): Int = config.getItInteger("general.time-out", Protocol.DEFAULT_TIMEOUT)
                    override fun getSocketTimeoutMillis(): Int = config.getItInteger("general.time-out", Protocol.DEFAULT_TIMEOUT)
                }
                return if (usePool){
                    val jedisPoolConfig = JedisPoolConfig()
                    jedisPoolConfig.testWhileIdle = config.getItBoolean("general.testWhileIdle",true)
                    val jedisPool = JedisPool(jedisPoolConfig, HostAndPort(host,port),jedisClientConfig)
                    VoidRedisClient(VoidJedisPoolClient(jedisPool))
                }else{
                    val jedis = Jedis(host,port,jedisClientConfig)
                    VoidRedisClient(VoidJedisClient(jedis))
                }
            }
            ClientType.LETTUCE ->{
                TODO("未完成")
            }
        }
        /*TODO 未完成*/
        TODO("未完成")
//        return VoidRedisClient(VoidJedisClient(JedisPool()))
    }
}