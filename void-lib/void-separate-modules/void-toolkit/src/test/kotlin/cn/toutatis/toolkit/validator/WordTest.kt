package cn.toutatis.toolkit.validator

import cn.toutatis.xvoid.toolkit.validator.XvoidWords
import org.junit.Test

class WordTest {

    @Test
    fun testSensitiveWordFilter() {
        val builtInSensitiveWordFilter = XvoidWords.getBuiltInSensitiveWordFilter()
        val search = builtInSensitiveWordFilter.search("大漠孤烟直销，长河落日元3P")
        System.err.println(search)
    }

}