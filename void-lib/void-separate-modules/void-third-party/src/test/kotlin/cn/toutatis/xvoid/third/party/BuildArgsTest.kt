package cn.toutatis.xvoid.third.party

import cn.toutatis.xvoid.third.party.openai.OpenaiAPI
import cn.toutatis.xvoid.toolkit.http.HttpToolkit
import org.junit.Assert
import org.junit.Test

class BuildArgsTest {

    @Test
    fun `test args`(): Unit {
        val argumentsSchema = OpenaiAPI.MODEL_LIST.getArgumentsSchema()
        System.err.println(argumentsSchema.checkHeaders())
        argumentsSchema.setHeader("Authorization", "foo")
        Assert.assertEquals(true,argumentsSchema.checkHeaders())
    }

    @Test
    fun `test api`(): Unit {
        val api = OpenaiAPI.MODEL_LIST
        HttpToolkit.post(api.getUrl(), null, api.getArgumentsSchema().getHeaders())
    }

}