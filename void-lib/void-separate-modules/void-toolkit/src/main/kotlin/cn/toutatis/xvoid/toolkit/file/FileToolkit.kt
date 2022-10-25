package cn.toutatis.xvoid.toolkit.file

import org.apache.commons.io.IOUtils
import org.slf4j.LoggerFactory
import sun.security.util.Resources
//import sun.security.util.Resources
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.net.URL
import java.util.*

/**
 * @author Toutatis_Gc
 * 文件工具箱
 */
object FileToolkit {

    private val logger = LoggerFactory.getLogger(FileToolkit::class.java)

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
     * Get the Properties file in the run directory.
     */
    @JvmStatic
    fun getResourcesProperties(filename:String): ResourceBundle {
        return Resources.getBundle(filename)
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
        return IOUtils.toString(file.toURI(),Charsets.UTF_8)
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