package cn.toutatis.xvoid.spring.business.user.service.impl;

import cn.toutatis.xvoid.data.common.security.SystemAuthRole;
import cn.toutatis.xvoid.data.common.security.SystemUserRoleRelation;
import cn.toutatis.xvoid.spring.business.user.persistence.SystemUserRoleRelationMapper;
import cn.toutatis.xvoid.spring.business.user.service.SystemUserRoleRelationService;
import cn.toutatis.xvoid.support.spring.config.orm.mybatisplus.support.VoidMybatisServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统用户&角色关系类 服务实现类
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-26
*/
@Service
public class SystemUserRoleRelationServiceImpl extends VoidMybatisServiceImpl<SystemUserRoleRelationMapper, SystemUserRoleRelation> implements SystemUserRoleRelationService {

}