package cn.toutatis.xvoid.spring.core.security;


/**
 * 认证方式
 * @author Toutatis_Gc
 * @see VoidSecurityAuthenticationService
 */
public enum AuthType {
    /**
     * 账号密码
     */
    ACCOUNT_NORMAL,

    /**
     * 账号密码+随机验证码
     */
    ACCOUNT_CHECK,

    /**
     * 令牌认证
     */
    TOKEN,

    /**
     *手机号或邮箱验证码认证
     */
    CHECK,

    /**
     * 第三方登录
     */
    THIRD,

    /**
     * 微信后端认证
     */
    WECHAT_LOGIN,

    /**
     * 统一远程平台登录
     */
    REMOTE_LOGIN,

    /**
     * 双重验证管理人员
     */
    REMOTE_DOUBLE_CHECK,

    /**
     * 太虚开发人员
     */
    VOID_DEV
}
