package cn.toutatis.xvoid.spring.business.user.service.impl;

import cn.toutatis.xvoid.data.common.security.SystemAuthPath;
import cn.toutatis.xvoid.data.common.security.SystemAuthRole;
import cn.toutatis.xvoid.data.common.security.SystemRolePathRelation;
import cn.toutatis.xvoid.spring.business.user.persistence.SystemAuthPathMapper;
import cn.toutatis.xvoid.spring.business.user.persistence.SystemRolePathRelationMapper;
import cn.toutatis.xvoid.spring.business.user.service.SystemAuthPathService;
import cn.toutatis.xvoid.support.spring.config.orm.mybatisplus.support.VoidMybatisServiceImpl;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 系统权限类 服务实现类
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-26
*/
@Service
public class SystemAuthPathServiceImpl extends VoidMybatisServiceImpl<SystemAuthPathMapper, SystemAuthPath> implements SystemAuthPathService {

    private final SystemRolePathRelationMapper systemRolePathRelationMapper;

    public SystemAuthPathServiceImpl(SystemRolePathRelationMapper systemRolePathRelationMapper) {
        this.systemRolePathRelationMapper = systemRolePathRelationMapper;
    }

    @Override
    public List<SystemAuthPath> getUserPermissions(List<SystemAuthRole> roles) {
        if (Validator.objNotNull(roles)){
            HashSet<String> roleIds = new HashSet<>(roles.size());
            for (SystemAuthRole role : roles) {
                roleIds.add(role.getId());
            }
            QueryWrapper<SystemRolePathRelation> systemRolePathRelationQueryWrapper = new QueryWrapper<>();
            systemRolePathRelationQueryWrapper.select("authId").in("roleId",roleIds);
            List<SystemRolePathRelation> rolePathsRelation = systemRolePathRelationMapper.selectList(systemRolePathRelationQueryWrapper);
            if (Validator.objNotNull(rolePathsRelation)){
                HashSet<String> pathIds = new HashSet<>(rolePathsRelation.size());
                for (SystemRolePathRelation paths : rolePathsRelation) {
                    pathIds.add(paths.getAuthId());
                }
                QueryWrapper<SystemAuthPath> authPathQueryWrapper = new QueryWrapper<>();
                authPathQueryWrapper.in("id",pathIds);
                return baseMapper.selectList(authPathQueryWrapper);
            }
        }
        return null;
    }

    @Override
    public List<String> getUserPermissionsStrings(List<SystemAuthRole> roles) {
        List<SystemAuthPath> userPermissions = this.getUserPermissions(roles);
        if (Validator.objNotNull(userPermissions)){
            ArrayList<String> paths = new ArrayList<String>();
            for (SystemAuthPath userPermission : userPermissions) {
                paths.add(userPermission.getPath());
            }
            return paths;
        }
        return null;
    }
}
