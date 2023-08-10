package cn.toutatis.xvoid.common.exception

import java.lang.RuntimeException

/**
 * @author Touatatis_Gc
 * 无法找到jsonobject对象key的异常
 */
class UnableToResolvedException : RuntimeException {
    /**
     * Constructs a `UnableToResolvedException` with no detail message.
     */
    constructor() {}

    /**
     * Constructs a `UnableToResolvedException` with the specified
     * detail message.
     *
     * @param   message   the detail message.
     */
    constructor(message: String?) : super(message) {}
}