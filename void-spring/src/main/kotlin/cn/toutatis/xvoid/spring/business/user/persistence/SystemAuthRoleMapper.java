package cn.toutatis.xvoid.spring.business.user.persistence;

import cn.toutatis.xvoid.orm.base.data.common.security.SystemAuthRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统角色类 Mapper 接口
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-25
*/
@Mapper
@Repository
public interface SystemAuthRoleMapper extends BaseMapper<SystemAuthRole> {


}
