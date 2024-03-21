package cn.toutatis.xvoid.common.exception.parameter

import cn.toutatis.xvoid.common.exception.base.VoidRuntimeException

open class ParameterException  : VoidRuntimeException {
    constructor()
    constructor(message: String?) : super(message)

}