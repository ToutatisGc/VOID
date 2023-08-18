package cn.toutatis.xvoid.spring.core.security.access

interface ValidationMessage {
    companion object {

        /**
         * 认证消息枚举
         * 手机验证码或者随机验证码错误
         */
        const val CHECK_CODE_ERROR = "验证码错误"

        const val USER_NOT_EXIST = "用户不存在"

        const val USERNAME_BLANK = "用户名不能为空"

        const val WRONG_IDENTIFY_FORMAT = "认证信息序列化错误"

        const val WRONG_IDENTIFY_TYPE = "错误的认证类型"

        const val NOT_OPENED_IDENTIFY_TYPE = "未开放的认证方式"

        const val REQUIRED_IDENTIFY_INFO = "认证信息为空"

        const val REQUIRED_IDENTIFY_PAYLOAD = "认证信息空荷载"

        const val ACCOUNT_LOCKED = "账户已锁定"

        const val ACCOUNT_LOCKED_TODAY = "今日不可登录"

        const val ACCOUNT_WILL_LOCK = "账户将于[%s]次输入后锁定"

        const val ACCOUNT_WILL_UNLOCK = "账户已锁定,将于[%s]解锁"

        const val ACCOUNT_DISABLED = "账户不允许登录"

        const val USERNAME_ILLEGAL = "用户名不合法"

        const val USERNAME_NOT_PRE_CHECK = "用户名未预检"

        const val AUTH_NOT_FOUND = "权限未识别"
        const val PARAMETER_NOT_FOUND = "缺失参数"
        const val PARAMETER_ERROR = "参数错误"
        const val USER_BANNED = "用户已被封禁"
        const val CONNECT_EXPIRED = "认证已过期"
        const val WRONG_ISSUER = "签发人错误"
        const val DEV_MODE_LOGIN = "仅开发模式使用"
        const val AUTH_EXCEPTION_LOGIN = "认证异常"
    }
}