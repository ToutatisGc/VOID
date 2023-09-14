package cn.toutatis.xvoid.spring.configure.system;

import cn.toutatis.xvoid.common.Version;
import cn.toutatis.xvoid.common.enums.ServerType;
import cn.toutatis.xvoid.common.exception.IllegalException;
import cn.toutatis.xvoid.spring.configure.system.enums.global.RunMode;
import cn.toutatis.xvoid.spring.configure.system.enums.qos.AntiLeechStrategy;
import cn.toutatis.xvoid.spring.configure.system.enums.storage.ObjectStorageMode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

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
