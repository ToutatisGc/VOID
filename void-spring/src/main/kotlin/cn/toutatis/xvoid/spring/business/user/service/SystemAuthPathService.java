package cn.toutatis.xvoid.spring.business.user.service;

import cn.toutatis.xvoid.orm.base.data.common.security.SystemAuthPath;
import cn.toutatis.xvoid.orm.base.data.common.security.SystemAuthRole;
import cn.toutatis.xvoid.spring.support.enhance.orm.mybatisplus.support.VoidMybatisService;

import java.util.List;

/**
 * <p>
 * 系统权限类 服务类
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-26
*/
public interface SystemAuthPathService extends VoidMybatisService<SystemAuthPath> {

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
