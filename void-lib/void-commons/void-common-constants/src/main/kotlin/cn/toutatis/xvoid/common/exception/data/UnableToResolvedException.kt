package cn.toutatis.xvoid.common.exception.data

import cn.toutatis.xvoid.common.exception.base.VoidRuntimeException

/**
 * 无法解析所抛出的异常
 * @author Toutatis_Gc
 */
class UnableToResolvedException : VoidRuntimeException {
    constructor()
    constructor(message: String?) : super(message)
}