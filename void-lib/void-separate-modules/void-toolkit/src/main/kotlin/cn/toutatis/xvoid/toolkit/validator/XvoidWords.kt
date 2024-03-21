package cn.toutatis.xvoid.toolkit.validator

import cn.toutatis.xvoid.toolkit.Meta
import cn.toutatis.xvoid.toolkit.clazz.ClassToolkit
import cn.toutatis.xvoid.toolkit.file.FileToolkit
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.debugWithModule
import cn.toutatis.xvoid.toolkit.log.errorWithModule
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.concurrent.Volatile

object XvoidWords {

    private val LOGGER = LoggerToolkit.getLogger(this.javaClass)

    /**
     * Sensitive Word File
     * 敏感词字典文件
     */
    private const val SENSITIVE_WORD_FILE = "sensitive-words.compress"

    @Volatile
    private var sensitiveWordFilter: SensitiveWordFilter? = null

    /**
     * 获取离线敏感词过滤器
     * 本工具资源内置敏感词字典
     * @return 敏感词过滤器
     */
    @JvmStatic
    fun getBuiltInSensitiveWordFilter(): SensitiveWordFilter {
        if (sensitiveWordFilter == null) {
            synchronized(XvoidWords::class.java) {
                if (sensitiveWordFilter == null) {
                    LOGGER.debugWithModule(Meta.MODULE_NAME,"初始化构建敏感词过滤器")
                    sensitiveWordFilter = SensitiveWordFilter()
                    val runningFromJar = ClassToolkit.isRunningFromJar(javaClass)
                    val ins =  if (runningFromJar){
                        FileToolkit.getJarResourceAsStream(SENSITIVE_WORD_FILE)
                    }else FileToolkit.getResourceFileAsFile(SENSITIVE_WORD_FILE)?.let { FileInputStream(it) }
                    if (ins != null){
                        LOGGER.debugWithModule(Meta.MODULE_NAME,"开始加载敏感词字典")
                        javaClass.getResourceAsStream(SENSITIVE_WORD_FILE)
                        val zipArchiveInputStream = ZipArchiveInputStream(ins,"UTF-8")
                        var entry: ZipArchiveEntry?
                        while (zipArchiveInputStream.nextZipEntry.also { entry = it } != null){
                            val entryName = entry!!.name
                            val fileSuffix = FileToolkit.getFileSuffix(entryName)
                            if ("dic".equals(fileSuffix,true)){
                                val reader = BufferedReader(InputStreamReader(zipArchiveInputStream))
                                var line: String?
                                var count = 0
                                while (reader.readLine().also { line = it } != null) {
                                    line?.let {
                                        sensitiveWordFilter!!.addSearchWord(it)
                                        count++
                                    }
                                }
                                LOGGER.debugWithModule(Meta.MODULE_NAME,"加载字典文件[$entryName],词汇数量[$count]")
                                reader.close()
                            }
                        }
                        zipArchiveInputStream.close()
                    }else{
                        val message = "无法找到[${SENSITIVE_WORD_FILE}]文件."
                        LOGGER.errorWithModule(Meta.MODULE_NAME,message)
                        throw NullPointerException(message)
                    }
                }
            }
        }
        return sensitiveWordFilter!!
    }
}
