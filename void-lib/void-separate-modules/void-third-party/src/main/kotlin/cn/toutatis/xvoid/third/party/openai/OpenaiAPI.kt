package cn.toutatis.xvoid.third.party.openai

import cn.hutool.http.Method
import cn.toutatis.xvoid.third.party.basic.BaseAPI
import cn.toutatis.xvoid.third.party.basic.annotations.APIDocument
import cn.toutatis.xvoid.third.party.openai.args.ModelListArgs
import cn.toutatis.xvoid.toolkit.http.base.ArgumentsSchema

@APIDocument(
    name = "OpenAI开发者平台接口",
    description = "内网一般无法访问,请科学上网",
    url = "https://platform.openai.com/"
)
enum class OpenaiAPI constructor(private val methodName:String,
                                 private val method:Method,
                                 private val url:String,
                                 private val argumentsSchema: ArgumentsSchema
                                 ) : BaseAPI {

    /**
     * ModelsList and describe the various models available in the API.
     * You can refer to the Models documentation to understand what models are available and the differences between them.
     * 模型列出并描述API中可用的各种模型。
     * 您可以参考Models文档来了解可用的模型以及它们之间的差异。
     */

    MODEL_LIST("获取模型列表", Method.POST,"https://api.openai.com/v1/models",ModelListArgs());

    override fun getMethodName(): String = this.methodName
    override fun getMethod(): Method = this.method
    override fun getUrl():String = this.url
    override fun getArgumentsSchema():ArgumentsSchema = this.argumentsSchema
}