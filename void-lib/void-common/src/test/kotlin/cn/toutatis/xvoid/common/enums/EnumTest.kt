package cn.toutatis.xvoid.common.enums

import com.alibaba.fastjson.JSONObject
import org.junit.Test

class EnumTest {

    @Test()
    fun `Test string to enum`(){
//        val valueOf = FileFields.valueOf("AAA")
//        System.err.println(valueOf)
        val jsonObject = JSONObject()
        jsonObject.put("key","FIELD_ORIGIN_NAME")
        val valueOf1 = FileFields.valueOf(jsonObject.getString("key"))
        System.err.println(valueOf1)
        val fileFields = jsonObject.getObject("key1", FileFields::class.java)
        System.err.println(fileFields)
    }

}