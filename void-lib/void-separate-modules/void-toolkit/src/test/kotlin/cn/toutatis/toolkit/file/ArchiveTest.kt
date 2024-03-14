package cn.toutatis.toolkit.file

import cn.toutatis.xvoid.toolkit.file.FileToolkit
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream
import org.junit.Test
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader


class ArchiveTest {

    @Test
    fun testUnzipFile(){
        val resourceFileAsFile = FileToolkit.getResourceFileAsFile("sensitive-words.compress")
        if (resourceFileAsFile != null){
            val zipArchiveInputStream = ZipArchiveInputStream(FileInputStream(resourceFileAsFile))
            var entry: ZipArchiveEntry?
            while (zipArchiveInputStream.nextZipEntry.also { entry = it } != null){
                System.err.println(entry)
                val fileSuffix = FileToolkit.getFileSuffix(entry!!.name)
                System.err.println(fileSuffix)
                val reader = BufferedReader(InputStreamReader(zipArchiveInputStream))
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    println("Line: $line")
                }
                reader.close()
            }
            zipArchiveInputStream.close()
        }
    }

}