package cn.toutatis.utils

import java.io.File
import java.net.URL
import java.net.URLClassLoader
import java.util.jar.JarFile


object PackageUtil {

    /**
     * 获取某包下（包括该包的所有子包）所有类
     *
     * @param packageName
     * 包名
     * @return 类的完整名称
     */
    fun getClassName(packageName: String): List<String>? {
        return getClassName(packageName, true)
    }

    /**
     * 获取某包下所有类
     *
     * @param packageName
     * 包名
     * @param childPackage
     * 是否遍历子包
     * @return 类的完整名称
     */
    fun getClassName(packageName: String, childPackage: Boolean): List<String>? {
        var fileNames: List<String>? = null
        val loader = Thread.currentThread().contextClassLoader
        val packagePath = packageName.replace(".", "/")
        val url: URL? = loader.getResource(packagePath)
        if (url != null) {
            val type: String = url.protocol
            if (type == "file") {
                fileNames = getClassNameByFile(url.getPath(), null, childPackage)
            } else if (type == "jar") {
                fileNames = getClassNameByJar(url.getPath(), childPackage)
            }
        } else {
            fileNames = getClassNameByJars((loader as URLClassLoader).urLs, packagePath, childPackage)
        }
        return fileNames
    }

    /**
     * 从项目文件获取某包下所有类
     *
     * @param filePath
     * 文件路径
     * @param className
     * 类名集合
     * @param childPackage
     * 是否遍历子包
     * @return 类的完整名称
     */
    private fun getClassNameByFile(filePath: String, className: List<String>?, childPackage: Boolean): List<String> {
        val myClassName: MutableList<String> = ArrayList()
        val file = File(filePath)
        val childFiles: Array<File> = file.listFiles()
        for (childFile in childFiles) {
            if (childFile.isDirectory()) {
                if (childPackage) {
                    myClassName.addAll(getClassNameByFile(childFile.getPath(), myClassName, childPackage))
                }
            } else {
                var childFilePath: String = childFile.getPath()
                if (childFilePath.endsWith(".class")) {
                    childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9,
                        childFilePath.lastIndexOf("."))
                    childFilePath = childFilePath.replace("\\", ".")
                    myClassName.add(childFilePath)
                }
            }
        }
        return myClassName
    }

    /**
     * 从jar获取某包下所有类
     *
     * @param jarPath
     * jar文件路径
     * @param childPackage
     * 是否遍历子包
     * @return 类的完整名称
     */
    private fun getClassNameByJar(jarPath: String, childPackage: Boolean): List<String> {
        val myClassName: MutableList<String> = ArrayList()
        val jarInfo = jarPath.split("!".toRegex()).toTypedArray()
        val jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"))
        val packagePath = jarInfo[1].substring(1)
        try {
            val jarFile = JarFile(jarFilePath)
            val entrys = jarFile.entries()
            while (entrys.hasMoreElements()) {
                val jarEntry = entrys.nextElement()
                var entryName = jarEntry.name
                if (entryName.endsWith(".class")) {
                    if (childPackage) {
                        if (entryName.startsWith(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."))
                            myClassName.add(entryName)
                        }
                    } else {
                        val index = entryName.lastIndexOf("/")
                        var myPackagePath: String
                        myPackagePath = if (index != -1) {
                            entryName.substring(0, index)
                        } else {
                            entryName
                        }
                        if (myPackagePath == packagePath) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."))
                            myClassName.add(entryName)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return myClassName
    }

    /**
     * 从所有jar中搜索该包，并获取该包下所有类
     *
     * @param urls
     * URL集合
     * @param packagePath
     * 包路径
     * @param childPackage
     * 是否遍历子包
     * @return 类的完整名称
     */
    private fun getClassNameByJars(urls: Array<URL>?, packagePath: String, childPackage: Boolean): List<String> {
        val myClassName: MutableList<String> = ArrayList()
        if (urls != null) {
            for (i in urls.indices) {
                val url: URL = urls[i]
                val urlPath: String = url.getPath()
                // 不必搜索classes文件夹
                if (urlPath.endsWith("classes/")) {
                    continue
                }
                val jarPath = "$urlPath!/$packagePath"
                myClassName.addAll(getClassNameByJar(jarPath, childPackage))
            }
        }
        return myClassName
    }
}