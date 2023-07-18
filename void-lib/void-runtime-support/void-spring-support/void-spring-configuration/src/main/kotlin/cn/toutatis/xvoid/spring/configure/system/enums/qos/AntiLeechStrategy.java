package cn.toutatis.xvoid.spring.configure.system.enums.qos;

/**
 * 盗链策略
 */
public enum AntiLeechStrategy {

    /**
     * 允许任何外部连接访问接口或文件
     */
    ALLOW,

    /**
     * 允许外链策略或自定义的行为进行访问
     */
    POLICY,

    /**
     * 拒绝任何外部服务访问
     */
    DENY;

}
