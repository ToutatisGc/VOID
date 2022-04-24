package cn.toutatis.toolkit.file

import org.junit.Test

/**
 * @author Toutatis_Gc
 * @date 2022/4/24 23:00
 */
class FileToolkitTest{

    @Test
    fun testFindFileInPossibleLocation() {
        val findFileInPossibleLocation = FileToolkit.INSTANCE.findFileInPossibleLocation("D:\\Git")
        System.err.println(findFileInPossibleLocation)
    }
}