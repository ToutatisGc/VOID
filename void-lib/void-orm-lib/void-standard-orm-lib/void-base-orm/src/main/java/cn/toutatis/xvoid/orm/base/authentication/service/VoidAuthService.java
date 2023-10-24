package cn.toutatis.xvoid.orm.base.authentication.service;

import cn.toutatis.xvoid.common.exception.AuthenticationException;
import cn.toutatis.xvoid.orm.base.authentication.entity.RequestAuthEntity;

/**
 * Void auth service
 * 认证服务接口
 * @constructor Create empty Void auth service
 */
public interface VoidAuthService {

    /**
     * Pre check
     * 用户名预检查
     * @param requestAuthEntity 用户实体
     * @return 账户是否存在
     */
    boolean preCheckAccount(RequestAuthEntity requestAuthEntity);

    /**
     * Throw failed
     * 抛出异常
     * @param message 异常消息
     */
    default void throwFailed(String message){
        throw new AuthenticationException(message);
    };
}
