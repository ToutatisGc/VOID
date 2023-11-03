package cn.toutatis.toolkit.validator

import cn.toutatis.xvoid.toolkit.constant.Regex
import org.junit.Assert
import org.junit.Test
import java.util.regex.Pattern

class RegexTest {

    @Test
    fun `test password regex`(){
        val pattern = Pattern.compile(Regex.PASSWORD_REGEX_04)
        // 长度限制 >= 8
        Assert.assertEquals(false,pattern.matcher("a123456").matches())
        // 长度限制 <= 32
        Assert.assertEquals(false,pattern.matcher("a12345678a12345678a12345678a12345678").matches())
        Assert.assertEquals(true,pattern.matcher("a12345678a12345678a12345678a1234").matches())
        // 限制有一位英文字母
        Assert.assertEquals(false,pattern.matcher("12345678").matches())
        Assert.assertEquals(true,pattern.matcher("a12345678").matches())
        Assert.assertEquals(true,pattern.matcher("Aa12345678").matches())
        // 限制符号
        Assert.assertEquals(true,pattern.matcher("a*12345678").matches())
        Assert.assertEquals(false,pattern.matcher("*12345678").matches())
        Assert.assertEquals(true,pattern.matcher("*a12345678").matches())
        Assert.assertEquals(false,pattern.matcher("*a1234 5678").matches())
        Assert.assertEquals(false,pattern.matcher("a12345678♠").matches())
    }

}