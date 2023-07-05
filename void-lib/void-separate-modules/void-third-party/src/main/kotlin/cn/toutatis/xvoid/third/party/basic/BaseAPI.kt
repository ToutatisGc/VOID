package cn.toutatis.xvoid.third.party.basic

import cn.hutool.http.Method
import cn.toutatis.xvoid.toolkit.http.base.ArgumentsSchema

interface BaseAPI<T:Enum<T>> {


    /**
     * Get name
     * 获取API名称
     * @return API名称
     */
    fun getMethodName(): String

    /**
     * Get method
     * 获取API所需HTTP-METHOD GET/POST/PUT...
     * @return
     */
    fun getMethod():Method

    /**
     * Get url
     * 获取API地址
     * @return
     */
    fun getUrl():String

    /**
     * Get arguments schema
     * 获取API参数定义
     * @return
     */
    fun getArgumentsSchema(): ArgumentsSchema

    /**
     * 输出API文档
     */
    fun printAPI() {

    }
}