package cn.toutatis.spring.core.security

interface ValidationMessage {
    companion object {

        /**
         * 验证消息时传出对象
         */
        const val VALIDATION_SESSION_KEY = "VALIDATION_LAST_OBJECT"

        /**
         * 认证消息枚举
         * 手机验证码或者随机验证码错误
         */
        const val CHECK_CODE_ERROR = "验证码错误"

        const val USER_NOT_EXIST = "用户%s不存在"

        const val WRONG_IDENTIFY_FORMAT = "认证格式错误"

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