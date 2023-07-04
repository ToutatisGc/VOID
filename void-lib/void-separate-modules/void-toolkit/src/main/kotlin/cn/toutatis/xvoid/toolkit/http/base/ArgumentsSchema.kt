package cn.toutatis.xvoid.toolkit.http.base

import cn.hutool.http.ContentType

interface ArgumentsSchema{

    fun getHeaders(): Map<String, String>

    fun setHeaders(map:Map<String,String>)

    fun setHeader(key:String,value:String)

    fun setParameters(map:Map<String,Any>)

    fun setParameter(key:String,value:String)

    fun setContentType(contentType:ContentType)

    fun checkHeaders():Boolean

    fun checkParameters():Boolean

    fun getAllParameters():List<Triple<String,Boolean,String>>?

}