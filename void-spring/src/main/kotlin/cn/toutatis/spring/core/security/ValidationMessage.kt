package cn.toutatis.spring.core.security

interface ValidationMessage {
    companion object {
        const val VALIDATION_SESSION_KEY = "VALIDATION_LAST_OBJECT"
        const val CHECK_CODE_ERROR = "验证码错误"
        const val AUTH_NOT_FOUND = "权限未识别"
        const val PARAMETER_NOT_FOUND = "缺失参数"
        const val PARAMETER_ERROR = "参数错误"
        const val USER_NOT_EXIST = "用户不存在"
        const val USER_BANNED = "用户已被封禁"
        const val CONNECT_EXPIRED = "认证已过期"
        const val WRONG_ISSUER = "签发人错误"
        const val DEV_MODE_LOGIN = "仅开发模式使用"
    }
}