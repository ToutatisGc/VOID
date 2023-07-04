package cn.toutatis.xvoid.third.party.aMap.args

import cn.toutatis.xvoid.toolkit.http.base.AbstractArgumentsSchema

class WeatherArgs : AbstractArgumentsSchema(
    checkParameterFields = null,
    checkHeadersFields = arrayListOf("key","city"),
    allParameterType = arrayListOf(
        Triple("[Get-Param]key",true,"高德开发者key")
    )) {
}