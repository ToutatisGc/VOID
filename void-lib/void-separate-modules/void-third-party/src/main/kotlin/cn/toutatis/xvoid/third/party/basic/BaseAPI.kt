package cn.toutatis.xvoid.third.party.basic

import cn.hutool.http.Method
import cn.toutatis.xvoid.toolkit.http.base.ArgumentsSchema

interface BaseAPI {

    fun getName(): String

    fun getMethod():Method

    fun getUrl():String

    fun getArgumentsSchema(): ArgumentsSchema

}