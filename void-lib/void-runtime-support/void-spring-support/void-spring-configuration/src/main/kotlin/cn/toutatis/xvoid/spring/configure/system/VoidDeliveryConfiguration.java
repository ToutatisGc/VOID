package cn.toutatis.xvoid.spring.configure.system;

import cn.toutatis.xvoid.common.enums.ServerType;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Toutatis_Gc
 * VOID环境下SpringBoot环境变量
 */
@ConfigurationProperties("void.delivery")
public class VoidDeliveryConfiguration {

    /**
     * 应用服务类型
     */
    private ServerType serverType = ServerType.PAAS;

    public ServerType getServerType() {
        return serverType;
    }

    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }
}
