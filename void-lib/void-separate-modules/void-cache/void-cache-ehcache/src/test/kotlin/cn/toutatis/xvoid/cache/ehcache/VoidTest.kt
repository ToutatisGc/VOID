package cn.toutatis.xvoid.cache.ehcache

import java.io.Serializable

class VoidTest<in T : Serializable> {

    fun test( s: T){
        println(s)
    }

}