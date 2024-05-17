package cn.toutatis.xvoid.orm.base.authentication.persistence;

import cn.toutatis.xvoid.orm.base.authentication.entity.SystemAuthPath;
import org.springframework.data.repository.CrudRepository;

/**
 * <p>
 * 系统权限类 JPA Repository 接口
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-26
*/
public interface SystemAuthPathRepository extends CrudRepository<SystemAuthPath,Integer> {

}
