package cn.toutatis.xvoid.common.exception;

/**
 * @author Toutatis_Gc
 * 无法throw异常的情况下
 * 在catch后应抛出此异常以保证事务正常进行
 * 仅作一个无法throw的情况下的明确标记异常类型
 */
public class ContinueTransactionException extends RuntimeException {

    /**
     * Constructs a {@code ContinueTransactionException} with no detail message.
     */
    public ContinueTransactionException() {
    }

    /**
     * Constructs a {@code ContinueTransactionException} with the specified
     * detail message.
     *
     * @param   message   the detail message.
     */
    public ContinueTransactionException(String message) {
        super(message);
    }
}
