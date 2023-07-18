package cn.toutatis.xvoid.spring.business.user.service.impl;

import cn.toutatis.xvoid.data.common.security.SystemRolePathRelation;
import cn.toutatis.xvoid.spring.business.user.persistence.SystemRolePathRelationMapper;
import cn.toutatis.xvoid.spring.business.user.service.SystemRolePathRelationService;
import cn.toutatis.xvoid.spring.support.config.orm.mybatisplus.support.VoidMybatisServiceImpl;
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
