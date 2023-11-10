package cn.toutatis.xvoid.orm.base.authentication.service.impl;

import cn.toutatis.xvoid.orm.base.authentication.entity.SystemAuthRole;
import cn.toutatis.xvoid.orm.base.authentication.entity.intermediate.SystemUserRoleIntermediate;
import cn.toutatis.xvoid.orm.base.authentication.persistence.SystemAuthRoleMapper;
import cn.toutatis.xvoid.orm.base.authentication.persistence.SystemUserRoleIntermediateMapper;
import cn.toutatis.xvoid.orm.base.authentication.service.SystemAuthRoleService;
import cn.toutatis.xvoid.orm.support.mybatisplus.VoidMybatisServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统角色类 服务实现类
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-25
*/
@Service
public class SystemAuthRoleServiceImpl extends VoidMybatisServiceImpl<SystemAuthRoleMapper, SystemAuthRole> implements SystemAuthRoleService {

    private final SystemUserRoleIntermediateMapper SystemUserRoleIntermediateMapper;

    public SystemAuthRoleServiceImpl(SystemUserRoleIntermediateMapper SystemUserRoleIntermediateMapper) {
        this.SystemUserRoleIntermediateMapper = SystemUserRoleIntermediateMapper;
    }

    @Override
    public List<SystemAuthRole> getUserRoles(String userId) {
        QueryWrapper<SystemUserRoleIntermediate> userRoleRelationQueryWrapper = new QueryWrapper<>();
        userRoleRelationQueryWrapper.select("roleId").eq("userId",userId);
//        if (config.getPlatformMode()){
//            userRoleRelationQueryWrapper.eq("belongTo", StandardFields.VOID_SQL_DEFAULT_CREATOR);
//        }
        List<SystemUserRoleIntermediate> relations = SystemUserRoleIntermediateMapper.selectList(userRoleRelationQueryWrapper);
        if (relations.size() == 0){
            return null;
        }else{
            List<String> roleIds = new ArrayList<>(relations.size());
            for (SystemUserRoleIntermediate relation : relations) {
                roleIds.add(relation.getRoleId());
            }
            QueryWrapper<SystemAuthRole> systemAuthRoleQueryWrapper = new QueryWrapper<>();
            systemAuthRoleQueryWrapper.in("id", roleIds);
            return mapper.selectList(systemAuthRoleQueryWrapper);
        }
    }
}
