package cn.toutatis.xvoid.spring.business.user.persistence;

import cn.toutatis.xvoid.data.common.security.SystemUserRoleRelation;
import cn.toutatis.xvoid.spring.support.enhance.orm.jpa.VoidJpaRepo;

/**
 * <p>
 * 系统用户&角色关系类 JPA Repository 接口
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-26
*/
public interface SystemUserRoleRelationRepository extends VoidJpaRepo<SystemUserRoleRelation,Integer> {

}
