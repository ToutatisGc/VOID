package cn.toutatis.xvoid.orm.base.authentication.service;

import cn.toutatis.xvoid.orm.base.authentication.entity.SystemAuthPath;
import cn.toutatis.xvoid.orm.base.authentication.entity.SystemAuthRole;
import cn.toutatis.xvoid.orm.support.VoidService;

import java.util.List;

/**
 * <p>
 * 系统权限类 服务类
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-26
*/
public interface SystemAuthPathService extends VoidService<SystemAuthPath> {

    /**
     * 获取用户角色下的权限信息
     * @param roles 角色
     * @return 权限信息
     */
    List<SystemAuthPath> getUserPermissions(List<SystemAuthRole> roles);

    /**
     * 获取用户角色下的权限信息
     * @param roles 角色
     * @return 权限信息
     */
    List<String> getUserPermissionsStrings(List<SystemAuthRole> roles);

}
