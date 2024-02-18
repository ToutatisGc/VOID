package cn.toutatis.xvoid.ddns

import cn.toutatis.xvoid.ddns.PkgInfo.MODULE_NAME
import cn.toutatis.xvoid.ddns.ip.commands.CommandInterpreter
import cn.toutatis.xvoid.toolkit.file.FileToolkit
import cn.toutatis.xvoid.toolkit.validator.Validator
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.*
import java.net.JarURLConnection
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess


/**
 * @author Toutatis_Gc
 * 在此文件运行main方法
 * */
fun main(args: Array<String>) {
    if (args.size == 1) {
        IPResolver(args[0].toBoolean())
    }else{
        IPResolver(false)
    }
}

internal object PkgInfo {
    const val MODULE_NAME = "VOID-RESOLVE"
}

/**
 * 主类
 * @param mode true 为指令模式 / false 为命令行模式
 * @param params 参数 没有配置文件下可用此方式加入配置
 */
class IPResolver(mode: Boolean, private val params: Map<String, String>? = null) {

    companion object{

        var lastRecord: String? = null

        /*日志*/
        private val logger: Logger = LoggerFactory.getLogger(IPResolver::class.java)

        /*固定线程池*/
        private var threadPool: ExecutorService = Executors.newFixedThreadPool(2)

        /*文件工具类*/
        val fileToolkit = FileToolkit

//        val osTools = OSTools.INSTANCE

        /*释放文件目录*/
        private const val RELEASE_DIR = "release"

        /*命令库前缀*/
        private const val CMD_SUFFIX = ".lib"

        /*运行类型是否为jar包*/
        var runTypeIsJar = true

        /*配置文件*/
        lateinit var config :Properties

        /*第三方网站池*/
        lateinit var urlPool : JSONArray

        /*命令解释器*/
        lateinit var commandInterpreter: CommandInterpreter

        /*运行状态*/
        var statusSign = 0

        var modea: Boolean = false

        fun getFile(filename:String):File{
            val file:File = if (runTypeIsJar){
                val runtimePath = fileToolkit.getRuntimePath(true)
                File("${runtimePath}/$RELEASE_DIR/${filename}")
            }else{
                File(fileToolkit.getResourceFile("$RELEASE_DIR/${filename}")!!.toURI())
            }
            return file
        }
    }

    init {
        logger.info("[${MODULE_NAME}]欢迎使用[VOID私转公网云解析工具]")
        if (!mode) {
            logger.info("[VOID-RESOLVE]加载中，请稍后...")
        }
        this.releaseFiles()
        logger.info("[${MODULE_NAME}]配置文件加载成功")
        this.loadConfig(params)
        logger.info("[${MODULE_NAME}]配置加载成功")
        commandInterpreter = this.loadCommandLibrary()
        logger.info("[${MODULE_NAME}]库加载成功")
        if (!mode) {
            logger.info("[${MODULE_NAME}]输入 help (h) 获取帮助")
            this.run()
        }else{
            this.auto()
        }
        modea = mode

        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                logger.info("[${MODULE_NAME}]正在退出")
                threadPool.shutdown()
            }
        })
    }

    fun auto(){
        threadPool = Executors.newSingleThreadScheduledExecutor()
        (threadPool as ScheduledExecutorService)
            .scheduleAtFixedRate(this.createCircleTask(), 0L, config.getProperty("Circles-Time").toLong(),TimeUnit.MINUTES)
    }

    private fun createCircleTask(): Runnable{
        return Runnable {
            commandInterpreter.play("simple-auto-resolve.playbook")
        }
    }

    fun run(): Unit {
        if (statusSign++ == 0){
            threadPool.execute(this.createCommandScanner())
            threadPool.shutdown()
        }
    }


    fun execute(command: String){
        commandInterpreter.execute(command)
    }

    private fun loadCommandLibrary():CommandInterpreter{
        val fileList = ArrayList<File>()
        val libs = getFile("libs")
        this.findFiles(fileList,libs)
        val tmpLib = JSONObject(16)
        val keySort = ArrayList<String>(16)
        fileList.forEach {
            logger.info("[${MODULE_NAME}]加载库:${it.name}")
            val obj: JSONObject = JSON.parseObject(fileToolkit.getFileContent(it))
            obj.entries.forEach { entry ->
                val uppercaseKey = entry.key.uppercase()
                keySort.add(uppercaseKey)
                if (!tmpLib.containsKey(uppercaseKey)){
                    tmpLib[uppercaseKey] = entry.value
                }else{
                    logger.warn("[${MODULE_NAME}]已存在命令${uppercaseKey}")
                    val levelObj = obj.getJSONObject(uppercaseKey)
                    val level = levelObj.getOrDefault("level", 0) as Int
                    val libObj = tmpLib.getJSONObject(uppercaseKey)
                    val libLevel = libObj.getOrDefault("level", 10) as Int
                    if (level > libLevel){
                        tmpLib[uppercaseKey] = levelObj
                        logger.warn("[${MODULE_NAME}]已覆盖命令${uppercaseKey},LEVEL:${level}")
                    }
                }
            }
        }
        keySort.sortBy { str -> str }
        val lib = JSONObject(16, true)
        keySort.forEach {
            lib[it] = tmpLib[it]
        }
        tmpLib.clear()
        return CommandInterpreter(lib)
    }

    private fun findFiles(list: ArrayList<File>,file:File){
        if(file.isDirectory){
            val listFiles = file.listFiles()
            if (listFiles!= null && listFiles.isNotEmpty()){
                for (listFile in listFiles) {
                    this.findFiles(list,listFile)
                }
            }
        }else{
            if (file.name.endsWith(CMD_SUFFIX)){
                list.add(file)
            }
        }
    }

    private fun createCommandScanner() : Runnable {
        return Runnable {
            while (true){
                print("=>")
                val br = BufferedReader(InputStreamReader(System.`in`))
                val input = br.readLine()
                if (input.isNotEmpty()){
                    when(input){
                        "EXIT","exit" ->{
                            logger.info("退出")
                            exitProcess(0)
                        }
                        else ->{
                            try {
                                execute(input)
                            }catch (e:Exception){
                                e.printStackTrace()
                                logger.info("${e.javaClass }:" + e.message)
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 加载必要配置文件
     */
    private fun loadConfig(params:Map<String,String>? = null) {
        val urlPool: File
        val config: File?
        /*运行在jar中的文件需要另外获取*/
        if (runTypeIsJar){
            val runtimePath = fileToolkit.getRuntimePath(true)
            urlPool = File("${runtimePath}/$RELEASE_DIR/url-pool.json")
            config = File("${runtimePath}/$RELEASE_DIR/config.properties")
        }else{
            urlPool =  File(fileToolkit.getResourceFile("$RELEASE_DIR/url-pool.json")!!.toURI())
            val configFile = fileToolkit.getResourceFile("$RELEASE_DIR/config.properties")
            config = configFile?.toURI()?.let { File(it) }
        }
        /*加载第三方网址*/
        var poolStr = ""
        val bufferedReader = BufferedReader(FileReader(urlPool))
        var line: String?
        while ((bufferedReader.readLine().also { line = it }) != null) {
            poolStr += line
        }
        bufferedReader.close()
        Companion.urlPool = JSON.parseArray(poolStr)
        /*加载配置文件*/
        val properties = Properties()
        config?.let { if (it.exists()){ properties.load(FileInputStream(it)) } }
        params?.forEach { (t, u) -> properties.setProperty(t,u) }
        if (Validator.strIsBlank(properties.getProperty("Ali-Access-Key-Id"))
            || Validator.strIsBlank(properties.getProperty("ALi-Access-Key-Secret"))
            || Validator.strIsBlank(properties.getProperty("Resolve-Domain"))){
            logger.error("[${MODULE_NAME}]请检查配置文件缺失值[config.properties [*]标必填属性]")
            exitProcess(-1)
        }
        Companion.config = properties
    }

    /**
     * 如果运行在jar中，将resources下的资源释放当前目录下
     */
    private fun releaseFiles(): Unit {
        val runtimePath = fileToolkit.getRuntimePath(true)
        val dirMk = File("$runtimePath/$RELEASE_DIR")
        if (fileToolkit.getRuntimePath(false).endsWith(".jar")){
            if (dirMk.exists() && dirMk.isDirectory){
                val jarResources = fileToolkit.getJarResource(RELEASE_DIR)
                val openConnection = jarResources!!.openConnection() as JarURLConnection
                val jarFile = openConnection.jarFile
                for (innerFile in jarFile.entries()) {
                    val name = innerFile.name
                    if (name.startsWith("$RELEASE_DIR/") && !name.endsWith("/")){
                        val jarResourcesStream = fileToolkit.getJarResourceAsStream(name)!!
                        val split = name.split("/")
                        var path = runtimePath
                        for (i in split.indices){
                            if (i != split.lastIndex){
                                path+= "/${split[i]}"
                                File(path).let { if (!it.exists()){ it.mkdir() } }
                            }
                        }
                        val nFile = File("$runtimePath/${name}")
                        if (!nFile.exists()){
                            val fileOutputStream = FileOutputStream(nFile)
                            val buff = ByteArray(1024)
                            var len: Int
                            while (0 <= jarResourcesStream.read(buff).also { len = it }) {
                                fileOutputStream.write(buff, 0, len)
                            }
                            fileOutputStream.flush()
                            fileOutputStream.close()
                            logger.info("文件缺失,释放 ${name}.")
                        }
                    }
                }
            }else{
                dirMk.mkdir()
                this.releaseFiles()
            }
        }else{
            runTypeIsJar = false
        }
    }

}



