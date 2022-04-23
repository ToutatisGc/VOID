package cn.toutatis.toolkit.json

import com.alibaba.fastjson.JSONObject

/**
 *@author Toutatis_Gc
 *@date 2022/4/17 12:53
 */
object JsonToolkit {

    /*TODO 获取以.分隔的key*/
    fun <T> getValue(obj : JSONObject, key: String, type:Class<T>): T {
        for (k in key.split(".")) {
            val t: T = obj.getObject(k, type)
        }
        return obj.getJSONObject("k") as T
    }

}