package cn.toutatis.xvoid.spring.business.user.persistence;

import cn.toutatis.xvoid.orm.base.authentication.entity.SystemRolePathRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统角色&权限关系类 Mapper 接口
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-26
*/
@Mapper
@Repository
public interface SystemRolePathRelationMapper extends BaseMapper<SystemRolePathRelation> {


}
