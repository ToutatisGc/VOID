package cn.toutatis.xvoid.spring.core.security.access.auth

import cn.toutatis.xvoid.common.standard.AuthFields
import cn.toutatis.xvoid.spring.core.security.access.AuthType
import com.alibaba.fastjson.JSONObject

/**
 * Request auth entity 请求实体
 * 请求登录接口传回的信息是一个json字符串
 * 在认证阶段将字符串转为此类对象
 * 此对象包含原始信息和转换信息
 * @constructor Create empty Request auth entity
 */
data class RequestAuthEntity(val rawInformation:JSONObject) {

    val account:String = rawInformation.getString(AuthFields.ACCOUNT)

    val authType: AuthType? = rawInformation.getObject(AuthFields.AUTH_TYPE,AuthType::class.java)

    val sessionId: String = rawInformation.getString(AuthFields.XVOID_INTERNAL_ACTIVITY_AUTH_SESSION_KEY)

}