package cn.toutatis.xvoid.third.party.openai.args

import cn.toutatis.xvoid.toolkit.http.base.AbstractArgumentsSchema

class ModelListArgs : AbstractArgumentsSchema(
    checkParameterFields = null,
    checkHeadersFields = arrayListOf("Authorization"),
    allParameterType = arrayListOf(
        Triple("[Header]Authorization",true,"OpenAI申请的api-key,值为[Bearer <api-key>]")
    )
)