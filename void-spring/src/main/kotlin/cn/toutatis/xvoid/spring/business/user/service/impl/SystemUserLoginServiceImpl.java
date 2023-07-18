package cn.toutatis.xvoid.spring.business.user.service.impl;

import cn.toutatis.xvoid.data.common.security.SystemUserLogin;
import cn.toutatis.xvoid.spring.business.user.persistence.SystemUserLoginMapper;
import cn.toutatis.xvoid.spring.business.user.service.SystemUserLoginService;
import cn.toutatis.xvoid.spring.support.config.orm.mybatisplus.support.VoidMybatisServiceImpl;
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
