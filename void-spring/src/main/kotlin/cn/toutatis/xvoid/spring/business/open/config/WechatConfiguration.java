package cn.toutatis.xvoid.spring.business.open.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Toutatis_Gc
 * @date 2022/10/25 12:42
 */
@Data
@ConfigurationProperties("void.wechat")
public class WechatConfiguration {

    /**
     * 微信开放平台原始ID
     */
    private String originalId;

    private OfficialAccountConfig officialAccountConfig = new OfficialAccountConfig();
    private PaymentConfig packageConfig = new PaymentConfig();

    /**
     * 公众号相关配置
     */
    @Data
    public static class OfficialAccountConfig{
        /**
         * 公众号appid
         */
        private String appId;

        /**
         * 公众号开发者密码
         */
        private String appSecret;

        /**
         * 公众号服务器配置
         * token
         */
        private String token;

        /**
         * 公众号服务器配置
         * EncodingAESKey
         */
        private String encodingAesKey;

        /**
         * 公众号服务器配置
         * 加密模式
         */
        private EncryptionType encryptionType = EncryptionType.PLAINTEXT;

    }

    /**
     * 微信支付相关配置
     */
    public static class PaymentConfig{

    }


    public OfficialAccountConfig getOfficialAccountConfig() {
        return officialAccountConfig;
    }

    public void setOfficialAccountConfig(OfficialAccountConfig officialAccountConfig) {
        this.officialAccountConfig = officialAccountConfig;
    }

    public PaymentConfig getPackageConfig() {
        return packageConfig;
    }

    public void setPackageConfig(PaymentConfig packageConfig) {
        this.packageConfig = packageConfig;
    }
}
