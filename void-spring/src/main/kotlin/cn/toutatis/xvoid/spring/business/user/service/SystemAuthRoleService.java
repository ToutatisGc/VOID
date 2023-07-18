package cn.toutatis.xvoid.spring.business.user.service;

import cn.toutatis.xvoid.data.common.security.SystemAuthRole;
import cn.toutatis.xvoid.spring.support.config.orm.mybatisplus.support.VoidMybatisService;

import java.util.List;

/**
 * <p>
 * 系统角色类 服务类
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-25
*/
public interface SystemAuthRoleService extends VoidMybatisService<SystemAuthRole> {


    /**
     * @param userId 用户ID
     * @return 用户角色
     */
    public List<SystemAuthRole> getUserRoles(String userId);

}
