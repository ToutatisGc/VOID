package cn.toutatis.xvoid.support.spring.amqp.persistence;

import cn.toutatis.xvoid.support.spring.amqp.entity.SystemLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统日志 Mapper 接口
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-12-04
*/
@Mapper
@Repository
public interface SystemLogMapper extends BaseMapper<SystemLog> {

}
