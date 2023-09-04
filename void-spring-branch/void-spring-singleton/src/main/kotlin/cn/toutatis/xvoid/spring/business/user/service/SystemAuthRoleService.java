package cn.toutatis.xvoid.spring.business.user.service;

import cn.toutatis.xvoid.orm.base.authentication.entity.SystemAuthRole;
import cn.toutatis.xvoid.orm.support.VoidService;

import java.util.List;

/**
 * <p>
 * 系统角色类 服务类
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-25
*/
public interface SystemAuthRoleService extends VoidService<SystemAuthRole> {


    /**
     * @param userId 用户ID
     * @return 用户角色
     */
    public List<SystemAuthRole> getUserRoles(String userId);

}
