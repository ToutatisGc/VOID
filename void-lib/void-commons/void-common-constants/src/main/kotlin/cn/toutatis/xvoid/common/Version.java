package cn.toutatis.xvoid.common;

/**
 * <p>版本号在系统中的作用为以下几点：<p/>
 * <p>1. 获取当前系统版本</p>
 * <p>2. 获取当前版本下的配置,并记录旧的配置</p>
 * <p>3. 提示更新</p>
 * @author Toutatis_Gc
 * @date 2022/11/3 14:04
 */
public enum Version {

    /**
     * 默认版本
     */
    $DEFAULT("0.0.0-ALPHA");

    private final String version;

    Version(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
