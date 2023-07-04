package cn.toutatis.toolkit.formatting

import cn.toutatis.xvoid.toolkit.formatting.StringToolkit
import org.junit.Test

class StringTest {

    @Test
    fun `test line wrapping`(){
        StringToolkit.lineWrap("|",2,"文章知识点与官方知识档案匹配，可进一步学习相关知识AAAA",3)
    }

}