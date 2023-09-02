package cn.toutatis.xvoid.spring.configure.system.enums.global

/**
 * @author Toutatis_Gc
 * @date 2022/10/26 21:16
 */
enum class RunMode {
    /**
     * 忽略权限控制
     * 允许所有方法/权限访问
     */
    DEBUG,

    /**
     * 开发模式
     * 此模式允许访问开发文档,并返回额外调试信息
     */
    DEV,

    /**
     * 生产模式
     * 此模式下隐藏调试信息,并禁止部分接口调用
     */
    PRODUCT
}