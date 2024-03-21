package cn.toutatis.xvoid.common.exception.parameter

/**
 * 缺失关键参数异常
 * @author Toutatis_Gc
 * @date 2022/11/19 9:36
 */
class MissingParameterException : ParameterException {
    constructor()
    constructor(message: String?) : super(message)
}