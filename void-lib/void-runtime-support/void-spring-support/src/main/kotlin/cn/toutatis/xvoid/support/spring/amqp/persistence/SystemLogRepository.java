package cn.toutatis.xvoid.support.spring.amqp.persistence;

import cn.toutatis.xvoid.support.spring.amqp.entity.SystemLog;
import cn.toutatis.xvoid.support.spring.config.orm.jpa.VoidJpaRepo;

import java.util.List;

/**
 * <p>
 * 系统日志 JPA Repository 接口
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-12-04
*/
public interface SystemLogRepository extends VoidJpaRepo<SystemLog,Integer> {

}
