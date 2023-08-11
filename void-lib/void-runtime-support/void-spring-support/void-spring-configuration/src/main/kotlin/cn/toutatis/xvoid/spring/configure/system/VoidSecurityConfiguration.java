package cn.toutatis.xvoid.spring.configure.system;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

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

    public LoginConfig getLoginConfig() {
        return loginConfig;
    }

    public void setLoginConfig(LoginConfig loginConfig) {
        this.loginConfig = loginConfig;
    }
}
