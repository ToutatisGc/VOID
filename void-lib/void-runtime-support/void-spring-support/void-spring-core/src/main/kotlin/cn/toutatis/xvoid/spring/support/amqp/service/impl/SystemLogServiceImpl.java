package cn.toutatis.xvoid.spring.support.amqp.service.impl;

import cn.toutatis.xvoid.spring.support.amqp.entity.SystemLog;
import cn.toutatis.xvoid.spring.support.amqp.persistence.SystemLogMapper;
import cn.toutatis.xvoid.spring.support.amqp.service.SystemLogService;
import cn.toutatis.xvoid.spring.support.config.orm.mybatisplus.support.VoidMybatisServiceImpl;
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
