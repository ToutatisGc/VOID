package cn.toutatis.xvoid.common.exception

import java.lang.RuntimeException

/**
 * @author Toutatis_Gc
 * @date 2022/11/19 9:36
 * 缺失关键参数异常
 */
class MissingParameterException : RuntimeException {
    constructor() {}
    constructor(message: String?) : super(message) {}
}