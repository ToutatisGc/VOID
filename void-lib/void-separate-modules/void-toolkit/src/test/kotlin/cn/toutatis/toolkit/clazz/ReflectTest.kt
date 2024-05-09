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

    @Test
    fun testGetFieldName(){
        val testEntity = TestEntity()
        val allFields = ReflectToolkit.getAllFields(TestEntity::class.java)
        for ((index, field) in allFields.withIndex()) {
            val fieldGetterMethodName = ReflectToolkit.getFieldGetterMethodName(field)
            System.err.println(fieldGetterMethodName)
        }
        testEntity.name = "ttt"
        System.err.println(testEntity)
        val getterMethods = ReflectToolkit.getGetterMethods(testEntity::class.java)
        getterMethods.forEach {
            System.err.println(it.name)
        }
    }

    @Test
    fun testGetterAndSetter(){
        val testEntity = TestEntity()
        ReflectToolkit.invokeFieldSetter("name",testEntity,"abc")
        val invokeFieldGetter = ReflectToolkit.invokeFieldGetter("name", testEntity)
        System.err.println(invokeFieldGetter)
    }

}