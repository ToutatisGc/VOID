package cn.toutatis.xvoid.data.common.security;

/**
 * @author Toutatis_Gc
 * 注册类型
 * @see cn.toutatis.xvoid.spring.core.route.background.auth.SecurityAuthController
 * 注册新用户使用此类
 */
public enum RegistryType {

    /**
     * 用户名密码注册类型
     */
    ACCOUNT,
    /**
     * 第三方注册类型
     * 微信，支付宝等
     */
    THIRD,
    /**
     * 邮箱注册类型
     */
    EMAIL,
    /**
     * 手机号注册类型
     */
    PHONE

}
