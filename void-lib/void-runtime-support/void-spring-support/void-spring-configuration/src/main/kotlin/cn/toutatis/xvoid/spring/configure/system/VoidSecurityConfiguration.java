package cn.toutatis.xvoid.spring.configure.system;

import cn.toutatis.xvoid.common.enums.RegistryType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

/**
 * VOID环境下安全配置
 * @author Toutatis_Gc
 */
@ConfigurationProperties("void.security")
public class VoidSecurityConfiguration {

    /**
     * 用户登录相关配置
     */
    private LoginConfig loginConfig = new LoginConfig();

    private DataConfig dataConfig = new DataConfig();

    private RegistryConfig registryConfig = new RegistryConfig();

    public static class LoginConfig{

        /**
         * 是否限制登录次数
         */
        private Boolean loginRetryLimitEnabled = true;

        /**
         * 登录前先检查用户名是否存在
         */
        private Boolean beforeLoginCheckUsername = true;

        /**
         * 登录重试次数
         */
        private Integer loginRetryTimes = 5;

        /**
         * 登录多次失败锁定时间
         */
        private Duration loginFailedLockTime = Duration.ofMinutes(5L);

        /**
         * 登录失败时间累加
         */
        private Boolean loginFailedLockTimeProgressiveIncrease = true;

        /**
         * 多次登录失败增加登录难度
         * 普通登录 -> 带验证码 -> 验证码&code&手机号验证码三重校验 -> 禁止登录
         */
        private Boolean loginFailedIncreaseDifficulty = true;


        public Boolean getLoginRetryLimitEnabled() {
            return loginRetryLimitEnabled;
        }

        public void setLoginRetryLimitEnabled(Boolean loginRetryLimitEnabled) {
            this.loginRetryLimitEnabled = loginRetryLimitEnabled;
        }

        public Boolean getBeforeLoginCheckUsername() {
            return beforeLoginCheckUsername;
        }

        public void setBeforeLoginCheckUsername(Boolean beforeLoginCheckUsername) {
            this.beforeLoginCheckUsername = beforeLoginCheckUsername;
        }

        public Integer getLoginRetryTimes() {
            return loginRetryTimes;
        }

        public void setLoginRetryTimes(Integer loginRetryTimes) {
            this.loginRetryTimes = loginRetryTimes;
        }

        public Duration getLoginFailedLockTime() {
            return loginFailedLockTime;
        }

        public void setLoginFailedLockTime(Duration loginFailedLockTime) {
            this.loginFailedLockTime = loginFailedLockTime;
        }

        public Boolean getLoginFailedLockTimeProgressiveIncrease() {
            return loginFailedLockTimeProgressiveIncrease;
        }

        public void setLoginFailedLockTimeProgressiveIncrease(Boolean loginFailedLockTimeProgressiveIncrease) {
            this.loginFailedLockTimeProgressiveIncrease = loginFailedLockTimeProgressiveIncrease;
        }

        public Boolean getLoginFailedIncreaseDifficulty() {
            return loginFailedIncreaseDifficulty;
        }

        public void setLoginFailedIncreaseDifficulty(Boolean loginFailedIncreaseDifficulty) {
            this.loginFailedIncreaseDifficulty = loginFailedIncreaseDifficulty;
        }
    }

    /**
     * 数据安全配置
     */
    public static class DataConfig{

        /**
         * 响应数据加密
         */
        private Boolean responseDataEncrypt = false;

        /**
         * 请求质量控制
         */
        private Boolean requestQos = true;

        public Boolean getResponseDataEncrypt() {
            return responseDataEncrypt;
        }

        public void setResponseDataEncrypt(Boolean responseDataEncrypt) {
            this.responseDataEncrypt = responseDataEncrypt;
        }

        public Boolean getRequestQos() {
            return requestQos;
        }

        public void setRequestQos(Boolean requestQos) {
            this.requestQos = requestQos;
        }
    }

    /**
     * 用户注册相关配置
     */
    public static class RegistryConfig{

        /**
         * 禁止新用户注册
         */
        private Boolean disableRegistry = false;

        /**
         * 多重绑定认证
         * 必须将指定所有认证方式全部绑定才视为正常用户
         */
        private RegistryType[] registrableType = {RegistryType.ACCOUNT};

        public Boolean getDisableRegistry() {
            return disableRegistry;
        }

        public void setDisableRegistry(Boolean disableRegistry) {
            this.disableRegistry = disableRegistry;
        }

        public RegistryType[] getRegistrableType() {
            return registrableType;
        }

        public void setRegistrableType(RegistryType[] registrableType) {
            this.registrableType = registrableType;
        }
    }

    public RegistryConfig getRegistryConfig() {
        return registryConfig;
    }

    public void setRegistryConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }

    public LoginConfig getLoginConfig() {
        return loginConfig;
    }

    public void setLoginConfig(LoginConfig loginConfig) {
        this.loginConfig = loginConfig;
    }

    public DataConfig getDataConfig() {
        return dataConfig;
    }

    public void setDataConfig(DataConfig dataConfig) {
        this.dataConfig = dataConfig;
    }
}
