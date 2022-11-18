package cn.toutatis.xvoid.common.standard

/**
 * 标准使用字段
 */
class StandardFields {

    companion object{

        /**
         * servlet filter 请求注入ID字段
         * 全局搜索 AnyPerRequestInjectRidFilter 类
         * 往往使用于拦截器注入request属性rid(Request-Id)的key使用
         */
        const val FILTER_REQUEST_ID_KEY = "VOID_RID"

        /**
         * 权限部分使用
         * 作为权限成功或失败的属性key使用,返回值通常为枚举
         * 需要强制自定义
         */
        const val VOID_AUTH_STATUS_KEY = "VOID_AUTH_STATUS"

        /**
         * TODO 多租户形态
         */
        const val VOID_REQUEST_HEADER_MCH_ID = "Void-mchId"

    }
}