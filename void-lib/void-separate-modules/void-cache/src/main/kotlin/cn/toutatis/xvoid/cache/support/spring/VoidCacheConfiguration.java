package cn.toutatis.xvoid.cache.support.spring;

import cn.toutatis.xvoid.cache.support.CacheType;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
* @author Toutatis_Gc
* @date 2022/6/26 14:12
 * 本地缓存配置类
*/
@ConfigurationProperties(prefix = "void.cache")
public class VoidCacheConfiguration {

    /**
     * 本地缓存路径
     */
    private String persistentPath;

    /**
     * 缓存文件夹名称
     */
    private String persistentName = "VOID_PERSISTENT_CACHE";

    /**
     * 使用缓存类型
     */
    private CacheType cacheType = CacheType.LOCAL;

    /**
     * 缓存配置文件配置
     */
    private CacheFileConfig cacheFileConfig;


    /**
     * 配置文件配置
     */
    public static class CacheFileConfig {

        /**
         * 配置文件存储路径
         */
        private String configPath;

        /**
         * 配置文件名称
         */
        private String configName = "void-ehcache.xml";

        public String getConfigPath() {
            return configPath;
        }

        public void setConfigPath(String configPath) {
            this.configPath = configPath;
        }

        public String getConfigName() {
            return configName;
        }

        public void setConfigName(String configName) {
            this.configName = configName;
        }
    }

    public CacheFileConfig getCacheFileConfig() {
        return cacheFileConfig;
    }

    public void setCacheFileConfig(CacheFileConfig cacheFileConfig) {
        this.cacheFileConfig = cacheFileConfig;
    }

    public String getPersistentPath() {
        return persistentPath;
    }

    public void setPersistentPath(String persistentPath) {
        this.persistentPath = persistentPath;
    }

    public String getPersistentName() {
        return persistentName;
    }

    public void setPersistentName(String persistentName) {
        this.persistentName = persistentName;
    }

    public CacheType getCacheType() {
        return cacheType;
    }

    public void setCacheType(CacheType cacheType) {
        this.cacheType = cacheType;
    }
}
