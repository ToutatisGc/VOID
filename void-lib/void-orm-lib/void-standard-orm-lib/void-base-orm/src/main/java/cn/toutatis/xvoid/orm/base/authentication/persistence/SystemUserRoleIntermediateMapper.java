package cn.toutatis.xvoid.orm.base.authentication.persistence;

import cn.toutatis.xvoid.orm.base.authentication.entity.intermediate.SystemUserRoleIntermediate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统用户&角色关系类 Mapper 接口
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-26
*/
@Mapper
@Repository
public interface SystemUserRoleIntermediateMapper extends BaseMapper<SystemUserRoleIntermediate> {


}
