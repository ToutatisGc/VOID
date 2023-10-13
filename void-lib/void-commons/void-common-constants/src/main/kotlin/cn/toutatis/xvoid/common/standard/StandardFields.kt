package cn.toutatis.xvoid.common.standard

/**
 * 标准使用字段
 */
class StandardFields {

    companion object{

        /**
         * 系统默认前缀
         * 可以使用 VOID. 或者 VOID_ 作为键前缀，视业务而定
         */
        const val SYSTEM_PREFIX = "VOID"
        /**
         * servlet filter 请求注入ID字段
         * 全局搜索 AnyPerRequestInjectRidFilter 类
         * 往往使用于拦截器注入request属性rid(Request-Id)的key使用
         */
        const val FILTER_REQUEST_ID_KEY = "${SYSTEM_PREFIX}_RID"

        /**
         * 作为权限成功或失败的属性key使用,返回值通常为枚举
         * 需要强制自定义
         */
        const val VOID_HTTP_ATTRIBUTE_STATUS_KEY = "${SYSTEM_PREFIX}_HTTP_STATUS_ATTRIBUTE"

        /**
         * httpServletRequest attribute
         * 作为http自定义消息
         */
        const val VOID_HTTP_ATTRIBUTE_MESSAGE_KEY = "${SYSTEM_PREFIX}_HTTP_MESSAGE_ATTRIBUTE"

        /**
         * TODO 多租户形态
         */
        const val VOID_REQUEST_HEADER_MCH_ID = "Xvoid-Tenant-Id"

        /**
         * 数据库默认创建者
         */
        const val VOID_BUSINESS_DEFAULT_CREATOR = "SYSTEM"

    }
}