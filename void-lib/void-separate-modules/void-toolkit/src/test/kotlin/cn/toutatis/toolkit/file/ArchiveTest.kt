package cn.toutatis.toolkit.file

import cn.toutatis.xvoid.toolkit.file.FileToolkit
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream
import org.junit.Test
import java.io.FileInputStream


class ArchiveTest {

    @Test
    fun testUnzipFile(){
        val resourceFileAsFile = FileToolkit.getResourceFileAsFile("sensitive-words.compress")
        if (resourceFileAsFile != null){
            println(resourceFileAsFile.name)
            val zipArchiveInputStream = ZipArchiveInputStream(FileInputStream(resourceFileAsFile))
            var entry: ZipArchiveEntry?
            while (zipArchiveInputStream.nextZipEntry.also { entry = it } != null){
                System.err.println(entry)
//                entry = zipArchiveInputStream.nextZipEntry
            }
            zipArchiveInputStream.close()
        }
//        FileInputStream(zipFile).use { fileInputStream ->
//            BufferedInputStream(fileInputStream).use { bufferedInputStream ->
//                ZipArchiveInputStream(bufferedInputStream).use { zipInputStream ->
//                    var entry: ZipArchiveEntry
//                    while (zipInputStream.nextZipEntry.also { entry = it } != null) {
//                        if (!entry.isDirectory) {
//                            println("Entry: " + entry.name)
//                            val outputStream = ByteArrayOutputStream()
//                            val buffer = ByteArray(1024)
//                            var length: Int
//                            while (zipInputStream.read(buffer).also { length = it } > 0) {
//                                outputStream.write(buffer, 0, length)
//                            }
//                            val entryData: ByteArray = outputStream.toByteArray()
//                            outputStream.close()
//
//                            // Do something with entryData
//                            // For example, you can write it to another file
//                            // FileOutputStream fileOutputStream = new FileOutputStream(entry.getName());
//                            // fileOutputStream.write(entryData);
//                            // fileOutputStream.close();
//                        }
//                    }
//                }
//            }
//        }

    }

}