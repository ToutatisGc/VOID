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

    const val LOGIN_PRE_CHECK_KEY = "${AUTH_KEY_PREFIX}:%s:PRE-CHECK"

    /**
     * Auth Times Key 账户登录重试次数
     */
    const val LOGIN_RETRY_TIMES_KEY = "${AUTH_KEY_PREFIX}:RETRY-TIMES:%s"

    const val LOGIN_ACCOUNT_SESSION_KEY = "${AUTH_KEY_PREFIX}:%s:LOGIN_ACCOUNT"

    const val LOGIN_ACCOUNT_LOCKED_KEY = "${AUTH_KEY_PREFIX}:LOCKED:%s"

}