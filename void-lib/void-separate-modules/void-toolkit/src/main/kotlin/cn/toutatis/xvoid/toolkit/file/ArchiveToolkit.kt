package cn.toutatis.xvoid.toolkit.file

import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream
import java.io.FileInputStream

/**
 * Archive toolkit
 * 压缩文件工具类
 * @author Toutatis_Gc
 * @constructor Create empty Archive toolkit
 */
object ArchiveToolkit {

    @JvmStatic
    fun extractZipArchive(filename:String): Unit {
        try {
            val fileInputStream = FileInputStream(filename).use {
                val zipArchiveInputStream = ZipArchiveInputStream(it).use {

                }
            }

        }catch (exception:RuntimeException){
            exception.printStackTrace()
        }finally {
//            fileInputStream.c
        }

    }
}