package cn.toutatis.xvoid.common.exception

import java.lang.RuntimeException

/**
 * @author Toutatis_Gc
 * 无法throw异常的情况下
 * 在catch后应抛出此异常以保证事务正常进行
 * 仅作一个无法throw的情况下的明确标记异常类型
 */
class ContinueTransactionException : RuntimeException {
    /**
     * Constructs a `ContinueTransactionException` with no detail message.
     */
    constructor() {}

    /**
     * Constructs a `ContinueTransactionException` with the specified
     * detail message.
     *
     * @param   message   the detail message.
     */
    constructor(message: String?) : super(message) {}
}