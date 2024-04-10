package cn.toutatis.toolkit.operation

import org.junit.Test

class BitTest {

    @Test
    fun `bit Test`(){
        var mask = 0 // 初始化掩码
        mask = mask or 0b0001 // 设置第一个标志位，即第0位
        println(mask)
        mask = mask and 0b0010 // 清除第一个标志位，即第0位
        println(mask)

    }

    @Test
    fun `getSysProperties`(){
        val properties = System.getProperties()
        properties.forEach { (k, v) -> println("$k:$v") }
    }

}