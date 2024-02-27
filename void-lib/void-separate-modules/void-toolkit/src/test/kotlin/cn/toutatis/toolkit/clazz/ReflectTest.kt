package cn.toutatis.toolkit.clazz

import cn.toutatis.xvoid.toolkit.clazz.ReflectToolkit
import org.apache.commons.lang3.RandomStringUtils
import org.junit.Test

class ReflectTest {

    @Test
    fun testGetField(){
        val child = Child()
        val fields = ReflectToolkit.getAllFields(Child::class.java)
        fields.forEach {
            ReflectToolkit.setObjectField(child,it,RandomStringUtils.randomAlphabetic(3))
        }
        System.err.println(fields)
        System.err.println(child.address)
    }

}