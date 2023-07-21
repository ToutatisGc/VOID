package cn.toutatis.xvoid.spring.business.user.service.impl;

import cn.toutatis.xvoid.orm.base.authentication.entity.SystemUserLogin;
import cn.toutatis.xvoid.spring.business.user.persistence.SystemUserLoginMapper;
import cn.toutatis.xvoid.spring.business.user.service.SystemUserLoginService;
import cn.toutatis.xvoid.orm.support.mybatisplus.VoidMybatisServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户类 服务实现类
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-07-13
*/
@Service
public class SystemUserLoginServiceImpl extends VoidMybatisServiceImpl<SystemUserLoginMapper, SystemUserLogin> implements SystemUserLoginService {

}
