package cn.toutatis.xvoid.orm.base.authentication.service;

import cn.toutatis.xvoid.orm.base.authentication.entity.SystemUserLogin;
import cn.toutatis.xvoid.orm.base.authentication.enums.RegistryType;
import cn.toutatis.xvoid.orm.support.VoidService;

/**
 * <p>
 * 系统用户类 服务类
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-07-13
*/
public interface SystemUserLoginService extends VoidService<SystemUserLogin> {

    /**
     * 预检查表中是否有该用户名
     * @param account 用户名
     * @return 表中有此用户
     */
    Boolean preCheckAccountExist(String account);

    /**
     * 检查注册用户名以及类型是否已经注册
     * @param account 账户名
     * @param registryType 注册类型
     * @return 用户是否注册
     */
    Boolean userInputRegistryExist(String account, RegistryType registryType);

}
