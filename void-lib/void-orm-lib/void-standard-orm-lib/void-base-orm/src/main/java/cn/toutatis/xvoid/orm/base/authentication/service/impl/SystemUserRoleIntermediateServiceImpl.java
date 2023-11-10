package cn.toutatis.xvoid.orm.base.authentication.service.impl;

import cn.toutatis.xvoid.orm.base.authentication.entity.intermediate.SystemUserRoleIntermediate;
import cn.toutatis.xvoid.orm.base.authentication.persistence.SystemUserRoleIntermediateMapper;
import cn.toutatis.xvoid.orm.base.authentication.service.SystemUserRoleIntermediateService;
import cn.toutatis.xvoid.orm.support.mybatisplus.VoidMybatisServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户&角色关系类 服务实现类
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-26
*/
@Service
public class SystemUserRoleIntermediateServiceImpl extends VoidMybatisServiceImpl<SystemUserRoleIntermediateMapper, SystemUserRoleIntermediate> implements SystemUserRoleIntermediateService {

}
