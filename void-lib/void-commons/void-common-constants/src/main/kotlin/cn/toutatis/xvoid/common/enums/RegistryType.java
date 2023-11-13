package cn.toutatis.xvoid.common.enums;

/**
 * @author Toutatis_Gc
 * 注册类型
 * @see cn.toutatis.xvoid.spring.core.route.background.auth.SecurityAuthController
 * 注册新用户使用此类
 */
public enum RegistryType {

    /**
     * 主要用于登录类型或注册类型校验
     * 例如限制注册类型
     */
    ALL,

    /**
     * 用户名密码注册类型
     */
    ACCOUNT,
    /**
     * 手机号注册类型
     */
    PHONE,
    /**
     * 邮箱注册类型
     */
    EMAIL,
    /**
     * 开发人员
     */
    DEVELOPER,
    /**
     * 管理员
     * *管理员类型不可注册,由开发人员直接指定
     */
    ADMINISTRATOR,

    /**
     * 第三方注册类型
     * [通用]
     */
    THIRD,
    /**
     * 微信OPEN ID
     */
    WECHAT,
    /**
     * 支付宝
     */
    ALI_PAY
}
