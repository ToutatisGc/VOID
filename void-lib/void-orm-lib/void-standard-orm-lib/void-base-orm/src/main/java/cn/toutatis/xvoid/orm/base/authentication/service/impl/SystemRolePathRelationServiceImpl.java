package cn.toutatis.xvoid.orm.base.authentication.service.impl;

import cn.toutatis.xvoid.orm.base.authentication.entity.SystemRolePathRelation;
import cn.toutatis.xvoid.orm.base.authentication.persistence.SystemRolePathRelationMapper;
import cn.toutatis.xvoid.orm.base.authentication.service.SystemRolePathRelationService;
import cn.toutatis.xvoid.orm.support.mybatisplus.VoidMybatisServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统角色&权限关系类 服务实现类
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-26
*/
@Service
public class SystemRolePathRelationServiceImpl extends VoidMybatisServiceImpl<SystemRolePathRelationMapper, SystemRolePathRelation> implements SystemRolePathRelationService {

}
