package cn.toutatis.xvoid.third.party.openai.args

import cn.toutatis.xvoid.toolkit.http.base.AbstractArgumentsSchema

class ModelListArgs : AbstractArgumentsSchema(
    checkParameterFields = null,
    checkHeadersFields = arrayListOf("Authorization")
)