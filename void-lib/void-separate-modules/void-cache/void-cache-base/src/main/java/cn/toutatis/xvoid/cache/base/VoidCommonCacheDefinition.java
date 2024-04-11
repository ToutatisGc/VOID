package cn.toutatis.xvoid.cache.base;

import java.io.Serializable;

/**
 * @author Toutatis_Gc
 * @date 2022/6/26 18:51
 * 通用环境缓存类型以及参数定义
 *
 * 默认缓存的键都为String类型，如果需要其他类型，可以自行定义
 */
public enum VoidCommonCacheDefinition {

    /**
     * 验证码缓存
     */
    VOID_SECURITY_CODE_CACHE("VOID_SECURITY_CODE_CACHE",String.class,String.class, DataExpiredPolicy.EXPIRED_CREATED,300L,true),

    ;

    /**
     * 缓存名称
     */
    private final String cacheName;

    /**
     * 健序列化类型
     */
    private final Class<? extends Serializable> keyClazz;

    /**
     * 值序列化类型
     */
    private final Class<? extends Serializable> valueClazz;

    /**
     * 缓存过期策略
     */
    private final DataExpiredPolicy expiredPolicy;

    /**
     * 缓存过期时间(单位:秒)
     */
    private final Long expiredTime;

    /**
     * 是否持久化缓存
     */
    private final Boolean persistent;


    VoidCommonCacheDefinition(String cacheName,
                              Class<? extends Serializable> keyClazz,
                              Class<? extends Serializable> valueClazz,
                              DataExpiredPolicy expiredPolicy, Long expiredTime,
                              Boolean persistent) {
        this.cacheName = cacheName;
        this.keyClazz = keyClazz;
        this.valueClazz = valueClazz;
        this.expiredPolicy = expiredPolicy;
        this.expiredTime = expiredTime;
        this.persistent = persistent;
    }

    public Boolean getPersistent() {
        return persistent;
    }

    public String getCacheName() {
        return cacheName;
    }

    public Class<? extends Serializable> getKeyClazz() {
        return keyClazz;
    }

    public Class<? extends Serializable> getValueClazz() {
        return valueClazz;
    }

    public DataExpiredPolicy getExpiredPolicy() {
        return expiredPolicy;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    /**
     * 缓存过期策略
     */
    public enum DataExpiredPolicy {

        /**
         * 不过期
         */
        NO_EXPIRED,

        /**
         * 创建后过期
         */
        EXPIRED_CREATED,

        /**
         * 访问后过期
         */
        EXPIRE_IDLE

    }

}
