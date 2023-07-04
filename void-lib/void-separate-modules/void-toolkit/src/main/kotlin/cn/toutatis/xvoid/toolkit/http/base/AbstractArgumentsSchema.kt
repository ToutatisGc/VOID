package cn.toutatis.xvoid.toolkit.http.base

import cn.hutool.http.ContentType
import cn.toutatis.xvoid.toolkit.VoidModuleInfo
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.errorWithModule

abstract class AbstractArgumentsSchema:ArgumentsSchema {

    private val logger = LoggerToolkit.getLogger(javaClass)

    public constructor()

    public constructor(checkParameterFields:List<String>?,
                       checkHeadersFields:List<String>?
    ){
        this.requiredParameterFields = checkParameterFields
        this.requiredHeadersFields = checkHeadersFields
    }

    public constructor(checkParameterFields:List<String>?,
                       checkHeadersFields:List<String>?,
                       allParameterType:List<Triple<String,Boolean,String>>
    ){
        this.requiredParameterFields = checkParameterFields
        this.requiredHeadersFields = checkHeadersFields
        this.allParameterType = allParameterType
    }

    private val headers = LinkedHashMap<String,String>()

    private val parameters = LinkedHashMap<String,Any>()

    private var requiredParameterFields:List<String>? = null

    private var requiredHeadersFields:List<String>? = null

    /**
     * 所需参数说明
     * [ 参数名称 | 是否必填 | 字段说明 ]
     */
    private var allParameterType:List<Triple<String,Boolean,String>>? = null

    init {
        headers["Content-Type"] = ContentType.JSON.value
    }


    override fun getHeaders(): Map<String, String> {
        return this.headers
    }

    override fun setHeaders(map: Map<String, String>) {
        headers.putAll(map)
    }

    override fun setHeader(key: String, value: String) {
        headers[key] = value
    }

    override fun setParameters(map: Map<String, Any>) {
        parameters.putAll(map)
    }

    override fun setParameter(key: String, value: String) {
        parameters[key] = value
    }

    override fun setContentType(contentType: ContentType) {
        headers["Content-Type"] = contentType.value
    }

    override fun checkHeaders(): Boolean {
        return this.checkContains(requiredHeadersFields,headers)
    }

    override fun checkParameters(): Boolean {
       return this.checkContains(requiredParameterFields,parameters)
    }

    private fun checkContains(list: List<String>?,map:Map<String,Any>): Boolean{
        var check = true
        if (list != null && list.isNotEmpty()){
            for (i in list.indices){
                val paramName = list[i]
                if (!map.containsKey(paramName)){
                    check = false
                    logger.errorWithModule(VoidModuleInfo.MODULE_NAME,"HTTP","缺失请求参数[${paramName}]")
                    break
                }
            }
        }
        return check
    }

    override fun toString(): String {
        return "AbstractArgumentsSchema(headers=$headers, parameters=$parameters, requiredParameterFields=$requiredParameterFields, requiredHeadersFields=$requiredHeadersFields, allParameterType=$allParameterType)"
    }
}