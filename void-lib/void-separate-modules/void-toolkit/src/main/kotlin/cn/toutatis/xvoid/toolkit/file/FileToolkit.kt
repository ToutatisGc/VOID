package cn.toutatis.xvoid.toolkit.file

//import sun.security.util.Resources
import cn.toutatis.xvoid.toolkit.constant.Regex
import org.apache.commons.io.IOUtils
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.net.URL
import java.util.regex.Pattern

/**
 * @author Toutatis_Gc
 * 文件工具箱
 */
object FileToolkit {

    private val logger = LoggerFactory.getLogger(FileToolkit::class.java)

    const val TEMP_FILE_DIR = "XV_TEMP"

    const val RESOURCE_FILE_DIR = "resources"

    /**
     * 为createDirectoryOrExist方法创建路径映射
     */
    private val dirPathMap = HashMap<String, Boolean>(32)

    @JvmStatic
    fun createDirectoryOrExist(dirPath:String): Boolean {
        if (dirPathMap.containsKey(dirPath)){
            return true
        }
        val dirFile = File(dirPath)
        return if (dirFile.exists()){
            if (dirFile.isDirectory){ true } else{ createDirectoryOrExist(dirPath) }
        }else{
            val mkdirSuccess = dirFile.mkdirs()
            dirPathMap[dirPath] = mkdirSuccess
            return mkdirSuccess
        }
    }

    @JvmStatic
    fun getResourcesFileAsString(filename:String): String? {
        return getResourcesFile(filename)?.readText(Charsets.UTF_8)
    }

    /**
     * Gets the files in the run directory.
     */
    @JvmStatic
    fun getResourcesFile(filename:String): URL?{
        return Thread.currentThread().contextClassLoader.getResource(filename)
    }

    /**
     * Gets the run path of the JAR.
     */
    @JvmStatic
    fun getRuntimePath(returnDir:Boolean): String {
        val codeSource = this.javaClass.protectionDomain.codeSource
        val runPath = File(codeSource.location.toURI().path)
        return if (returnDir) runPath.parent else runPath.path
    }

    /**
     * Gets the run path of the javaClass.
     */
    @JvmStatic
    fun getRuntimePath(clazz: Class<*>): String {
        val codeSource = clazz.protectionDomain.codeSource
        val runPath = File(codeSource.location.toURI().path)
        return runPath.path
    }

    @JvmStatic
    fun getThreadPath(): String {
        return Thread.currentThread().contextClassLoader.getResource("")!!.path
    }

    /**
     * Get the resource files in the JAR package.
     */
    @JvmStatic
    fun getJarResource(filename:String): URL? {
        return this.javaClass.classLoader.getResource(filename)
    }

    /**
     * Get the resource files in the JAR package.
     */
    @JvmStatic
    fun getJarResourceAsStream(filename:String) : InputStream?{
        return this.javaClass.classLoader.getResourceAsStream(filename)
    }

    /**
     * Convert a file to a string.
     */
    @JvmStatic
    fun getFileContent(file:File): String{
        if (file.exists()){
            return IOUtils.toString(file.toURI(),Charsets.UTF_8)
        }else{
            throw FileNotFoundException("${file.path}不存在.")
        }
    }

    /**
     * 获取文件路径文件名后缀
     */
    @JvmStatic
    fun getFileSuffix(fileName: String): String {
        return fileName.substring(fileName.lastIndexOf(".") + 1).lowercase()
    }

    @JvmStatic
    fun getFileSuffix(file: File): String {
        if(file.exists()){
            return getFileSuffix(file.name)
        }
        throw FileNotFoundException("文件不存在")
    }

    /**
     * 检测是否为图片
     * 其实应该用文件头检查,但是目前没空,先用文件名检查
     */
    @JvmStatic
    fun isImg(suffix: String): Boolean {
        val matcher = Pattern.compile(Regex.IMAGE_SUFFIX_REGEX).matcher(suffix)
        return matcher.matches()
    }

    @JvmStatic
    fun exists(file: File): Boolean {
        return file.exists()
    }

    @JvmStatic
    fun findFileInPossibleLocation(path:String): File {
        if (File(path).exists()){
            return File(path).apply {
                logger.info("Found the file!this file has a clear path!Nice!")
            }
        }else{
            val resourcesFile = this.getResourcesFile(path)
            if (resourcesFile != null && File(resourcesFile.toURI()).exists()){
                return File(resourcesFile.toURI()).apply {
                    logger.trace("Found the file!this file is a resource file,please use getResource method! ")
                }
            }
            val jarResource = this.getJarResource(path)
            if (jarResource != null && File(jarResource.toURI()).exists()){
                return File(jarResource.toURI()).apply {
                    logger.trace("Found the file!this file is a JAR package file,please use getJarResource method! ")
                }
            }
            throw FileNotFoundException("Can't find [$path] this file location. QAQ")
        }
    }
}