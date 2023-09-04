package cn.toutatis.xvoid.spring.business.open.config;

/**
 * @author Toutatis_Gc
 * @date 2022/10/25 12:50
 * 微信公众号服务器配置加解密类型
 */
public enum EncryptionType {
    /**
     * 明文模式
     */
    PLAINTEXT,
    /**
     * 兼容模式
     */
    COMPATIBILITY,
    /**
     * 加密模式
     */
    ENCRYPTION
}
