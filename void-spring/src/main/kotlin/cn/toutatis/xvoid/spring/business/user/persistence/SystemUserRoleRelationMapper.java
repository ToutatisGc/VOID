package cn.toutatis.xvoid.spring.business.user.persistence;

import cn.toutatis.xvoid.orm.base.data.common.security.SystemUserRoleRelation;
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
public interface SystemUserRoleRelationMapper extends BaseMapper<SystemUserRoleRelation> {


}
