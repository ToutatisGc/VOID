package cn.toutatis.xvoid.ddns

import cn.toutatis.xvoid.common.standard.StringPool
import cn.toutatis.xvoid.common.standard.StringPool.SLASH
import cn.toutatis.xvoid.ddns.Meta.Companion.MODULE_NAME
import cn.toutatis.xvoid.ddns.commands.CommandInterpreter
import cn.toutatis.xvoid.ddns.constance.CommonConstance
import cn.toutatis.xvoid.ddns.constance.CommonConstance.CMD_SUFFIX
import cn.toutatis.xvoid.ddns.constance.CommonConstance.CONFIG_FILE_NAME
import cn.toutatis.xvoid.ddns.constance.CommonConstance.RELEASE_DIR
import cn.toutatis.xvoid.ddns.constance.CommonConstance.THIRD_PARTY_URL_POOL_FILE_NAME
import cn.toutatis.xvoid.ddns.constance.LibKeys
import cn.toutatis.xvoid.ddns.constance.ParamConstance.ALI_ACCESS_KEY_ID_PARAM
import cn.toutatis.xvoid.ddns.constance.ParamConstance.ALI_ACCESS_KEY_SECRET_PARAM
import cn.toutatis.xvoid.ddns.constance.ParamConstance.RESOLVE_DOMAIN_PARAM
import cn.toutatis.xvoid.toolkit.file.FileToolkit
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.errorWithModule
import cn.toutatis.xvoid.toolkit.log.infoWithModule
import cn.toutatis.xvoid.toolkit.validator.Validator
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import org.slf4j.Logger
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
 * 形参：args[0]
 *      True 为剧本模式：TODO
 *      False 为命令行模式：命令行模式为手动输入命令调用
 * 在此文件运行main方法
 * */
fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        val programArgumentList = args.toList()
        val paramMapping = HashMap<String, String?>()
        // 0 位置为运行类型
        val runType = DynamicDNSResolver.getArg(programArgumentList, 0, Boolean::class.java)!! as Boolean
        // 1 为自动运行的剧本
        val playbook = DynamicDNSResolver.getArg(programArgumentList, 1, String::class.java) as String?
        playbook?.let { paramMapping[LibKeys.PLAYBOOK] = playbook }
        DynamicDNSResolver(runType,paramMapping)
    }else{
        DynamicDNSResolver(false)
    }
}


/**
 * 主类
 * @param runMode true 为指令模式 / false 为命令行模式
 * @param params 参数 没有配置文件下可用此方式加入配置
 */
class DynamicDNSResolver(runMode: Boolean, private val params: Map<String, String?>? = null) {

    /*第一次运行释放文件夹*/
    private var firstReleaseFolder = false

    /*Logger 日志*/
    private val logger: Logger = LoggerToolkit.getLogger(this::class.java)

    /*运行状态*/
    private var statusSign = 0

    companion object{

        /*命令解释器*/
        lateinit var COMMAND_INTERPRETER: CommandInterpreter

        /*最后一次解析记录*/
        var lastRecord: String? = null

        /*配置文件*/
        lateinit var config : Properties

        /*第三方网站池*/
        lateinit var urlPool : JSONArray

        /*运行类型是否为jar包*/
        private var runTypeIsJar = true

        /*运行模式和缓存*/
        var modeCache: Boolean = false

        /**
         * Thread pool 固定线程池
         */
        private var _THREAD_POOL: ExecutorService = Executors.newFixedThreadPool(2)

        /**
         * Get resource file
         * 获取资源文件
         * @param filename 文件名
         * @return 文件
         */
        fun getResourceFile(filename:String):File{
            val resource = if (runTypeIsJar){
                val runtimePath = FileToolkit.getRuntimePath(true)
                File("${runtimePath}$SLASH$RELEASE_DIR$SLASH${filename}")
            }else{
                val file = FileToolkit.getResourceFile("$RELEASE_DIR$SLASH${filename}")
                    ?.toURI()?.let { File(it) } ?: throw FileNotFoundException("找不到文件 $filename")
                file
            }
            return resource
        }

        /**
         * 获取参数
         *
         * @param T 参数类型泛型
         * @param list 参数列表
         * @param index 参数位置
         * @param type 参数类型
         * @return 参数
         */
        fun <T> getArg(list : List<String>,index: Int,type:Class<T>) : Any? {
            val hasIndex = list.size > index && list[index].isNotEmpty()
            when (type){
                Boolean::class.java -> if (hasIndex) {
                    if (list[index].uppercase() == StringPool.TRUE.uppercase()) {
                        return true
                    }else if (list[index].uppercase() == StringPool.FALSE.uppercase()){
                        return false
                    }
                }else return false
                String::class.java -> return if (hasIndex) { list[index] } else null
            }
            return null
        }
    }

    /**
     * 初始化
     * 1.释放资源文件
     * 2.加载配置
     */
    init {
        logger.infoWithModule(MODULE_NAME,"欢迎使用[VOID动态IP解析工具]")
        if (!runMode) {
            logger.infoWithModule(MODULE_NAME,"加载中，请稍后...")
        }
        // 释放或加载资源文件
        this.releaseSupportFiles()
        logger.info("[${MODULE_NAME}]配置文件加载成功")
        this.loadConfig(params)
        logger.info("[${MODULE_NAME}]配置加载成功")
        COMMAND_INTERPRETER = this.loadCommandLibrary()
        logger.info("[${MODULE_NAME}]库加载成功")
        if (!runMode) {
            logger.info("[${MODULE_NAME}]输入 help (h) 获取帮助")
            this.run()
        }else{
            val playbook = config.getProperty(LibKeys.PLAYBOOK)
            if (Validator.strNotBlank(playbook)){
                this.autoStartRunningPlaybook(playbook)
            }else{
                logger.debug("[${MODULE_NAME}]未配置剧本，请自行继承功能.")
            }
        }
        modeCache = runMode

        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                logger.info("[${MODULE_NAME}]正在退出")
                _THREAD_POOL.shutdown()
            }
        })
    }

    /**
     * Auto start running playbook
     * 剧本模式下自动运行剧本
     */
    private fun autoStartRunningPlaybook(playbook: String){
        val playbookInfo = COMMAND_INTERPRETER.getPlaybookInfo(playbook)
        if (playbookInfo != null){
            // 将线程池转为定时线程
            _THREAD_POOL = Executors.newSingleThreadScheduledExecutor()
            val scheduledExecutorService = _THREAD_POOL as ScheduledExecutorService
            val playTimerConfig = playbookInfo.getJSONObject(LibKeys.PLAYBOOK_TIMER)
            val interval:Int
            val delay:Int
            if (playTimerConfig != null){
                interval = playTimerConfig.getOrDefault(LibKeys.PLAYBOOK_TIMER_INTERVAL,CommonConstance.PLAYBOOK_TIMER_INTERVAL_DEFAULT) as Int
                delay = playTimerConfig.getOrDefault(LibKeys.PLAYBOOK_TIMER_DELAY,CommonConstance.PLAYBOOK_TIMER_DELAY_DEFAULT) as Int
            }else{
                interval = CommonConstance.PLAYBOOK_TIMER_INTERVAL_DEFAULT
                delay = CommonConstance.PLAYBOOK_TIMER_DELAY_DEFAULT
            }
            playTimerConfig[LibKeys.PLAYBOOK_TIMER_INTERVAL] = interval
            playTimerConfig[LibKeys.PLAYBOOK_TIMER_DELAY] = delay
            val task = Runnable { COMMAND_INTERPRETER.play(playbook,playbookInfo) }
            logger.infoWithModule(MODULE_NAME,"启动剧本定时器，剧本[$playbook]将每[${interval}]分钟执行一次.")
            scheduledExecutorService.scheduleAtFixedRate(task, delay.toLong(), interval.toLong(), TimeUnit.MINUTES)
        }else{
            logger.errorWithModule(MODULE_NAME,"未找到剧本[$playbook],即将退出")
            exitProcess(-1)
        }

    }

    private fun run() {
        if (statusSign++ == 0){
            _THREAD_POOL.execute(this.createCommandScanner())
            _THREAD_POOL.shutdown()
        }
    }


    fun execute(command: String){
        COMMAND_INTERPRETER.execute(command)
    }

    private fun loadCommandLibrary(): CommandInterpreter {
        val fileList = ArrayList<File>()
        val libs = getResourceFile("commands")
        this.findFiles(fileList,libs)
        val tmpLib = JSONObject(16)
        val keySort = ArrayList<String>(16)
        fileList.forEach {
            logger.info("[${MODULE_NAME}]加载库:${it.name}")
            val obj: JSONObject = JSON.parseObject(FileToolkit.getFileContent(it))
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

    private fun createCommandScanner() : Runnable = Runnable {
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


    /**
     * 加载必要配置文件
     */
    private fun loadConfig(params:Map<String,String?>? = null) {
        val urlPool: File
        val config: File?
        /*运行在jar中的文件需要另外获取*/
        if (runTypeIsJar){
            val runtimePath = FileToolkit.getRuntimePath(true)
            urlPool = File("$runtimePath$SLASH$RELEASE_DIR$SLASH$THIRD_PARTY_URL_POOL_FILE_NAME")
            config = File("$runtimePath$SLASH$RELEASE_DIR$SLASH$CONFIG_FILE_NAME")
        }else{
            urlPool =  File(FileToolkit.getResourceFile("$RELEASE_DIR$SLASH$THIRD_PARTY_URL_POOL_FILE_NAME")!!.toURI())
            val configFile = FileToolkit.getResourceFile("$RELEASE_DIR$SLASH$CONFIG_FILE_NAME")
            config = configFile?.toURI()?.let { File(it) }
        }
        /*加载第三方网址*/
        Companion.urlPool = JSON.parseArray(FileToolkit.getFileContent(urlPool))
        /*加载配置文件*/
        val properties = Properties()
        config?.let { if (it.exists()){ properties.load(FileInputStream(it)) } }
        params?.forEach { (k, v) -> properties.setProperty(k,v) }
        if (Validator.strIsBlank(properties.getProperty(ALI_ACCESS_KEY_ID_PARAM))
            || Validator.strIsBlank(properties.getProperty(ALI_ACCESS_KEY_SECRET_PARAM))
            || Validator.strIsBlank(properties.getProperty(RESOLVE_DOMAIN_PARAM))){
            if (firstReleaseFolder){
                logger.info("[${MODULE_NAME}]请检查[${RELEASE_DIR}]文件夹并配置文件[${CONFIG_FILE_NAME}]配置项并重新运行.")
                exitProcess(0)
            }else{
                logger.error("[${MODULE_NAME}]请检查配置文件缺失值[${CONFIG_FILE_NAME} [*]标必填属性]")
                exitProcess(-1)
            }
        }
        Companion.config = properties
    }

    /**
     * 如果运行在jar中，将resources下的资源释放当前目录下
     */
    private fun releaseSupportFiles() {
        val runtimePath = FileToolkit.getRuntimePath(true)
        val dirMk = File("$runtimePath$SLASH$RELEASE_DIR")
        // 如果是Jar包，释放resource文件夹到运行目录
        if (FileToolkit.getRuntimePath(false).endsWith(StringPool.JAR_SUFFIX)){
            if (dirMk.exists() && dirMk.isDirectory){
                val jarResources = FileToolkit.getJarResource(RELEASE_DIR)
                val openConnection = jarResources!!.openConnection() as JarURLConnection
                val jarFile = openConnection.jarFile
                for (innerFile in jarFile.entries()) {
                    val name = innerFile.name
                    if (name.startsWith("$RELEASE_DIR$SLASH") && !name.endsWith(SLASH)){
                        val jarResourcesStream = FileToolkit.getJarResourceAsStream(name)!!
                        val split = name.split(SLASH)
                        var path = runtimePath
                        for (i in split.indices){
                            if (i != split.lastIndex){
                                path+= "$SLASH${split[i]}"
                                File(path).let { if (!it.exists()){ it.mkdir() } }
                            }
                        }
                        val writeFile = File("$runtimePath$SLASH${name}")
                        if (!writeFile.exists()){
                            logger.info("文件缺失,释放文件[${name}].")
                            val fileOutputStream = FileOutputStream(writeFile)
                            val buff = ByteArray(1024)
                            var len: Int
                            while (0 <= jarResourcesStream.read(buff).also { len = it }) {
                                fileOutputStream.write(buff, 0, len)
                            }
                            fileOutputStream.flush()
                            fileOutputStream.close()
                        }
                    }
                }
            }else{
                firstReleaseFolder = dirMk.mkdir()
                this.releaseSupportFiles()
            }
        }else{
            runTypeIsJar = false
        }
    }

}



