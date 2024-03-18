package cn.toutatis.toolkit.validator

import cn.toutatis.xvoid.toolkit.validator.XvoidWords
import org.junit.Assert
import org.junit.Test

class WordTest {

    @Test
    fun testSensitiveWordFilter() {
        val builtInSensitiveWordFilter = XvoidWords.getBuiltInSensitiveWordFilter()
        val search = builtInSensitiveWordFilter.search("检查")
        Assert.assertEquals(1, search.size)
    }

}