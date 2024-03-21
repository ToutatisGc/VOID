package cn.toutatis.xvoid.common.exception.base

/**
 * VOID标准运行时异常
 * @author Toutatis_Gc
 * VOID环境所属下的所有异常都应当继承此异常
 */
open class VoidRuntimeException : RuntimeException {

    /**
     * Message can be display
     * 错误信息是否可被公开展示
     */
    var messageCanBeDisplay = false

    /**
     * 异常空构造器
     */
    constructor()

    /**
     * 错误信息构造器
     */
    constructor(message: String?)

}