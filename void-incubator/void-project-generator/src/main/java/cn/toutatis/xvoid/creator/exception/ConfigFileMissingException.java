package cn.toutatis.xvoid.creator.exception;

import java.io.IOException;

/**
 * @author Toutatis
 * 文件丢失抛出此异常
 */
public class ConfigFileMissingException extends IOException {

    public static final String  MISSING_MESSAGE = "[config.properties]配置文件丢失或无法找到!";

    public ConfigFileMissingException(String message, Throwable cause) {
        super(message, cause);
    }
}
