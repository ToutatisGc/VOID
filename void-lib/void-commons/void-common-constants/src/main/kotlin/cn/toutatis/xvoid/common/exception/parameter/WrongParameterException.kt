package cn.toutatis.xvoid.common.exception.parameter

/**
 * 缺失关键参数异常
 * @author Toutatis_Gc
 * @date 2024/03/21 14:42
 */
class WrongParameterException : ParameterException {
    constructor()
    constructor(message: String?) : super(message)
}