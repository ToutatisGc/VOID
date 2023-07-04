package cn.toutatis.xvoid.third.party.aMap.args

import cn.toutatis.xvoid.toolkit.http.base.AbstractArgumentsSchema

class WeatherArgs : AbstractArgumentsSchema(
    checkParameterFields = null,
    checkHeadersFields = arrayListOf("key","city"),
    allParameterType = arrayListOf(
        Triple("[Get-Param] key",true," 请求服务权限标识 | 用户在高德地图官网申请web服务API类型KEY"),
        Triple("[Get-Param] city",true,"城市编码 | 输入城市的adcode，adcode信息可参考城市编码表 https://lbs.amap.com/api/webservice/download" ),
        Triple("[Get-Param] extensions",false,"气象类型 | 可选值：base返回实况天气/all返回预报天气"),
        Triple("[Get-Param] output",false,"返回格式 | 可选值：JSON(Default),XML"),
    )) {
}