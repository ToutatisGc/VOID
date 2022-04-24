package cn.toutatis.toolkit.json

import com.alibaba.fastjson.JSONObject
import org.junit.Assert
import org.junit.Test

/**
 *@author Toutatis_Gc
 *@date 2022/4/23 11:09
 */
class JSONTest {

    @Test
    fun init(): Unit {
        System.err.println(JsonToolkit)
        val jsonObject1 = JSONObject()
        jsonObject1.put("name","abc")
        System.err.println(jsonObject1.hashCode())
        val jsonObject2 = JSONObject()
        jsonObject2.put("name","abc")
        System.err.println(jsonObject2.hashCode())
        Assert.assertEquals(16,JsonToolkit.DEFAULT_MAX_RECORD)
    }

    @Test
    fun setMaxRecordNum(): Unit {
        JsonToolkit.setMaxRecordNum(16)
    }

    @Test
    fun separatorTest(): Unit {
        val jsonObject2 = JSONObject();
        jsonObject2.put("test","success")
        val jsonObject1 = JSONObject()
        jsonObject1.put("inner",jsonObject2)
        val jsonObject = JSONObject()
        jsonObject.put("obj",jsonObject1)
        val value = JsonToolkit.getValue(jsonObject, "obj.inner.test", String::class.java)
        Assert.assertEquals("success",value)
    }

    @Test
    fun bigIteratorTest(): Unit {
        var originObj = JSONObject()
        val num = 100
        var key = ""
        for (i in num downTo 0){
            val jsonObject = JSONObject()
            if (i == num){
                jsonObject["key"] = "success"
                originObj = jsonObject
            }else{
                jsonObject["obj-${i}"] = "value-$i"
                jsonObject["obj-${i}"] = originObj
                originObj = jsonObject
            }
        }
        for (i in 0 until num) key += "obj-${i}."
        key += "key"
        var record = System.currentTimeMillis()
        val value = JsonToolkit.getValue(originObj, key, String::class.java)
        System.err.println("[${value}]用时:"+(System.currentTimeMillis() - record)+"ms")
        System.err.println(JsonToolkit.getInnerMap())
        record = System.currentTimeMillis()
        val value1 = JsonToolkit.getValue(originObj, key, String::class.java)
        System.err.println("[${value1}]INDEX用时:"+(System.currentTimeMillis() - record)+"ms")
    }

    @Test
    fun singleKeyTest(): Unit {
        val jsonObject = JSONObject()
        jsonObject.put("obj","abc")
        val value = JsonToolkit.getValue(jsonObject, "obj", String::class.java)
        Assert.assertEquals("abc","abc")
    }

}