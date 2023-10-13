package cn.toutatis.xvoid.common.exception

import java.lang.RuntimeException

/**
 * 极为严重的错误,通常发生在渗透现象
 * @author Toutatis_Gc
 * @date 2022/11/19 9:36
 */
class IllegalException : RuntimeException {
    constructor(message: String?) : super(message) {}
}