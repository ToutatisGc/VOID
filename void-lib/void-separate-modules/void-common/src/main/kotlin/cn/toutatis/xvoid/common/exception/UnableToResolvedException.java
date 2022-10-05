package cn.toutatis.xvoid.common.exception;

/**
 * @author Touatatis_Gc
 * 无法找到jsonobject对象key的异常
 */
public class UnableToResolvedException extends RuntimeException {

    /**
     * Constructs a {@code UnableToResolvedException} with no detail message.
     */
    public UnableToResolvedException() {
    }

    /**
     * Constructs a {@code UnableToResolvedException} with the specified
     * detail message.
     *
     * @param   message   the detail message.
     */
    public UnableToResolvedException(String message) {
        super(message);
    }
}
