package cn.toutatis.xvoid.orm.base.authentication.service.impl;

import cn.toutatis.xvoid.orm.base.authentication.entity.intermediate.SystemRolePathIntermediate;
import cn.toutatis.xvoid.orm.base.authentication.persistence.SystemRolePathIntermediateMapper;
import cn.toutatis.xvoid.orm.base.authentication.service.SystemRolePathIntermediateService;
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
public class SystemRolePathIntermediateServiceImpl extends VoidMybatisServiceImpl<SystemRolePathIntermediateMapper, SystemRolePathIntermediate> implements SystemRolePathIntermediateService {

}
