package cn.toutatis.toolkit.http

import cn.toutatis.xvoid.toolkit.http.HttpToolkit
import org.junit.Test

/**
 * @author Toutatis_Gc
 * @date 2022/10/6 20:43
 *
 */
class HttpRequestTest {

    @Test
    fun getTest(){
        val get = HttpToolkit.syncGet("https://www.baidu.com", null)
        System.err.println(get)
    }

}