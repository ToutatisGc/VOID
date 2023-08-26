package cn.toutatis.xvoid.common.standard

/**
 * Auth fields
 * 权限认证标准字段
 * @constructor Create empty Auth fields
 */
object AuthFields {

    /**
     * Auth Type 认证类型
     */
    const val AUTH_TYPE = "authType"

    /**
     * Username 用户名
     */
    const val USERNAME = "username"

    /**
     * Auth Key Prefix Redis Key前缀
     */
    const val AUTH_KEY_PREFIX = "${StandardFields.SYSTEM_PREFIX}:AUTH"

    /**
     * Login Pre Check Key 用户名预检Key
     * The placeholder is the JSessionId in the cookie 占位符为cookies的JSessionId
     */
    const val LOGIN_PRE_CHECK_KEY = "${AUTH_KEY_PREFIX}:%s:PRE-CHECK"

    /**
     * Login Account Session Key 当前用户session正在登录的用户名
     * The placeholder is the JSessionId in the cookie 占位符为cookies的JSessionId
     */
    const val LOGIN_ACCOUNT_SESSION_KEY = "${AUTH_KEY_PREFIX}:%s:LOGIN_ACCOUNT"
    /**
     * Auth Times Key 账户登录重试次数
     * The placeholder is the user name 占位符为用户名
     */
    const val LOGIN_RETRY_TIMES_KEY = "${AUTH_KEY_PREFIX}:RETRY-TIMES:%s"

    /**
     * Login Account Locked Key 当前锁定用户
     * The placeholder is the user name 占位符是用户名
     */
    const val LOGIN_ACCOUNT_LOCKED_KEY = "${AUTH_KEY_PREFIX}:LOCKED:%s"

}