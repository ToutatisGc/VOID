package cn.toutatis.xvoid.spring.support.amqp.persistence;

import cn.toutatis.xvoid.spring.support.amqp.entity.SystemLog;
import cn.toutatis.xvoid.spring.support.jpa.VoidJpaRepo;

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
