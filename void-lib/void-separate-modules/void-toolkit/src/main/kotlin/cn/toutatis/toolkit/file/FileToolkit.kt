package cn.toutatis.toolkit.file

import org.apache.commons.io.IOUtils
import sun.security.util.Resources
import java.io.File
import java.io.InputStream
import java.net.URL
import java.util.*


enum class FileToolkit {

    INSTANCE;

    fun getResourcesFileAsString(filename:String): String? {
        return getResourcesFile(filename)?.readText(Charsets.UTF_8)
    }

    fun getResourcesFile(filename:String): URL?{
        return Thread.currentThread().contextClassLoader.getResource(filename)
    }

    fun getResourcesProperties(filename:String): ResourceBundle {
        return Resources.getBundle(filename)
    }

    fun getRuntimePath(returnDir:Boolean): String {
        val codeSource = this.javaClass.protectionDomain.codeSource
        val runPath = File(codeSource.location.toURI().path)
        return if (returnDir) runPath.parent else runPath.path
    }

    fun getJarResource(filename:String): URL? {
        return this.javaClass.classLoader.getResource(filename)
    }

    fun getJarResourceAsStream(filename:String) : InputStream?{
        return this.javaClass.classLoader.getResourceAsStream(filename)
    }

    fun getFileContent(file:File): String{
        return IOUtils.toString(file.toURI(),Charsets.UTF_8)
    }
}