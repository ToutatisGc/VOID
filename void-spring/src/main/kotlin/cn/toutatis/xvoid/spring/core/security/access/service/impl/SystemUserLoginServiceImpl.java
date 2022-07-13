package cn.toutatis.xvoid.spring.core.security.access.service.impl;

import cn.toutatis.data.common.security.SystemUserLogin;
import cn.toutatis.xvoid.spring.core.security.access.persistence.SystemUserLoginMapper;
import cn.toutatis.xvoid.spring.core.security.access.service.SystemUserLoginService;
import cn.toutatis.xvoid.support.spring.config.orm.mybatisplus.support.VoidMybatisServiceImpl;
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
