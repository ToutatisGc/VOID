package cn.toutatis.xvoid.common;

/**
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
