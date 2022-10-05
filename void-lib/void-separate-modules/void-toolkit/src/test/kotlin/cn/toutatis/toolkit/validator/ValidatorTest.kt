package cn.toutatis.toolkit.validator

import cn.toutatis.xvoid.toolkit.validator.Validator
import org.junit.Assert
import org.junit.Test

/**
 * @author Toutatis_Gc
 * @date 2022/10/6 0:58
 *
 */
class ValidatorTest {

    @Test
    fun strTest() {
        //空字符串测试
        Assert.assertEquals("空字符串测试",true,Validator.strIsBlank(""))
        Assert.assertEquals("空字符串测试",true,Validator.strIsBlank(" "))
        Assert.assertEquals("空字符串测试",true,Validator.strIsBlank(null))
        Assert.assertEquals("空字符串测试",true,Validator.strIsBlank("null"))
        Assert.assertEquals("空字符串测试",true,Validator.strIsBlank("NULL"))
        Assert.assertEquals("空字符串测试",false,Validator.strIsBlank("abc"))
        //字符串测试
        Assert.assertEquals("字符串测试",true,Validator.strNotBlank("aaa"))
        Assert.assertEquals("字符串测试",false,Validator.strNotBlank(" "))
        Assert.assertEquals("字符串测试",false,Validator.strNotBlank("NULL"))
    }

    @Test
    fun numTest(){
        //数字测试
        Assert.assertEquals("数字测试",true,Validator.strIsNumber("123"))
        Assert.assertEquals("数字测试",true,Validator.strIsNumber("-123"))
        Assert.assertEquals("数字测试",true,Validator.strIsNumber("123.55"))
        Assert.assertEquals("数字测试",true,Validator.strIsNumber("0"))
        Assert.assertEquals("数字测试",false,Validator.strIsNumber("-"))
    }


}