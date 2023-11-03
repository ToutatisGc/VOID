package cn.toutatis.xvoid.common.standard

/**
 * 认证消息常量
 * @author Toutatis_Gc
 */
interface AuthValidationMessage {
    companion object {

        const val CHECK_CODE_ERROR = "验证码错误"

        const val ACCOUNT_NOT_EXIST = "用户不存在"

        const val ACCOUNT_ALREADY_EXIST = "用户已存在"

        const val ACCOUNT_REGISTRY_SUCCESS = "用户注册成功"

        const val ACCOUNT_REGISTRY_FAILED = "用户注册失败,请重试"

        const val ACCOUNT_DISABLE_REGISTRY = "禁止新用户注册"

        const val USERNAME_BLANK = "用户名不能为空"

        const val WRONG_IDENTIFY_FORMAT = "认证信息序列化错误"

        const val WRONG_IDENTIFY_TYPE = "错误的认证类型"

        const val NOT_OPENED_IDENTIFY_TYPE = "未开放的认证方式"

        const val REQUIRED_IDENTIFY_INFO = "认证信息为空"

        const val REQUIRED_IDENTIFY_PAYLOAD = "认证信息空荷载"

        const val ACCOUNT_LOCKED = "账户已锁定"

        const val ACCOUNT_LOCKED_TODAY = "今日登录已锁定,请次日尝试"

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

        const val SECRET_NOT_EQUALS = "密码不一致"

        const val SECRET_NOT_MATCH = "密码格式错误,密码长度为[8-32]位支持英文及常见字符"

        const val ACCOUNT_NOT_MATCH = "账户名格式错误,密码长度为[2-32]位支持中文,下划线,英文及数字"

        const val SECRET_NOT_FILLED = "请填写密码"
    }
}