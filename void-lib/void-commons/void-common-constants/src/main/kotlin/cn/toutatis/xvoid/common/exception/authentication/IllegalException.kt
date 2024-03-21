package cn.toutatis.xvoid.common.exception.authentication

import cn.toutatis.xvoid.common.exception.base.VoidRuntimeException

/**
 * 极为严重的错误,通常发生在渗透现象
 * @author Toutatis_Gc
 * @date 2022/11/19 9:36
 */
class IllegalException: VoidRuntimeException {
    constructor()
    constructor(message: String?) : super(message)
}