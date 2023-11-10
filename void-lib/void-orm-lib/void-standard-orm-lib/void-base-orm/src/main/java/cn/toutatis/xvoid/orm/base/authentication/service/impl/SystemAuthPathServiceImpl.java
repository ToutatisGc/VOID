package cn.toutatis.xvoid.orm.base.authentication.service.impl;

import cn.toutatis.xvoid.orm.base.authentication.entity.SystemAuthPath;
import cn.toutatis.xvoid.orm.base.authentication.entity.SystemAuthRole;
import cn.toutatis.xvoid.orm.base.authentication.entity.intermediate.SystemRolePathIntermediate;
import cn.toutatis.xvoid.orm.base.authentication.persistence.SystemAuthPathMapper;
import cn.toutatis.xvoid.orm.base.authentication.persistence.SystemRolePathIntermediateMapper;
import cn.toutatis.xvoid.orm.base.authentication.service.SystemAuthPathService;
import cn.toutatis.xvoid.orm.support.mybatisplus.VoidMybatisServiceImpl;
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

    private final SystemRolePathIntermediateMapper SystemRolePathIntermediateMapper;

    public SystemAuthPathServiceImpl(SystemRolePathIntermediateMapper SystemRolePathIntermediateMapper) {
        this.SystemRolePathIntermediateMapper = SystemRolePathIntermediateMapper;
    }

    @Override
    public List<SystemAuthPath> getUserPermissions(List<SystemAuthRole> roles) {
        if (Validator.objNotNull(roles)){
            HashSet<String> roleIds = new HashSet<>(roles.size());
            for (SystemAuthRole role : roles) {
                roleIds.add(role.getId());
            }
            QueryWrapper<SystemRolePathIntermediate> SystemRolePathIntermediateQueryWrapper = new QueryWrapper<>();
            SystemRolePathIntermediateQueryWrapper.select("authId").in("roleId",roleIds);
            List<SystemRolePathIntermediate> rolePathsRelation = SystemRolePathIntermediateMapper.selectList(SystemRolePathIntermediateQueryWrapper);
            if (Validator.objNotNull(rolePathsRelation)){
                HashSet<String> pathIds = new HashSet<>(rolePathsRelation.size());
                for (SystemRolePathIntermediate paths : rolePathsRelation) {
                    pathIds.add(paths.getAuthId());
                }
                QueryWrapper<SystemAuthPath> authPathQueryWrapper = new QueryWrapper<>();
                authPathQueryWrapper.in("id",pathIds);
                return mapper.selectList(authPathQueryWrapper);
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
