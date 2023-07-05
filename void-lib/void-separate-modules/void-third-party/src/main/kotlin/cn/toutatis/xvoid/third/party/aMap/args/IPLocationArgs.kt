package cn.toutatis.xvoid.third.party.aMap.args

import cn.toutatis.xvoid.toolkit.http.base.AbstractArgumentsSchema

class IPLocationArgs : AbstractArgumentsSchema(
    checkParameterFields = null,
    checkHeadersFields = arrayListOf("key","city"),
    allParameterType = arrayListOf(
        Triple("[Get-Param] key",true," 请求服务权限标识 | 用户在高德地图官网申请web服务API类型KEY"),
        Triple("[Get-Param] ip",false," IP地址 | 需要搜索的IP地址（仅支持国内）若用户不填写IP，则取客户http之中的请求来进行定位"),
        Triple("[Get-Param] sig",false," 签名 | 选择数字签名认证的付费用户必填"),
    )) {
}