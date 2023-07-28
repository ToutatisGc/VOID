package cn.toutatis.xvoid.orm.base.infrastructure.services;

import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog;
import cn.toutatis.xvoid.orm.base.infrastructure.persistence.SystemLogMapper;
import cn.toutatis.xvoid.orm.support.mybatisplus.VoidMybatisServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-12-04
*/
@Service
public class SystemLogServiceImpl extends VoidMybatisServiceImpl<SystemLogMapper, SystemLog> implements SystemLogService {

}
