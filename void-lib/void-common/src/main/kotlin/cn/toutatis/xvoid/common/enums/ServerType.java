package cn.toutatis.xvoid.common.enums;

/**
 * 服务类型
 * @author Toutatis_Gc
 */
public enum ServerType {

    /**
     * 自运营平台服务
     * 由官方授权的平台服务
     */
    PAAS,

    /**
     * 受监管项目
     * 监管阶段: 打包[向服务端传输校验信息] -> 启动[从服务器匹配校验信息] ->
     * 运行[定时向服务端校验信息]
     * 向官方上报运行情况和授权信息
     */
    SUPERVISION,

    /**
     * 离线运行项目
     * 本地运行,不再维护
     */
    OFFLINE

}
