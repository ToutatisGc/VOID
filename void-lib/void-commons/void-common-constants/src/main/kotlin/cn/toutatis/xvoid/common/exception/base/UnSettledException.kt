package cn.toutatis.xvoid.common.exception.base

/**
 * VOID 未处理的异常
 * @author Toutatis_Gc
 */
class UnSettledException : VoidRuntimeException {
    constructor()
    constructor(message: String?) : super(message)
}