package cn.toutatis.xvoid.spring.core.security.access.persistence;

import cn.toutatis.data.common.security.SystemUserLogin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统用户类 Mapper 接口
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-07-13
*/
@Mapper
@Repository
public interface SystemUserLoginMapper extends BaseMapper<SystemUserLogin> {


}
