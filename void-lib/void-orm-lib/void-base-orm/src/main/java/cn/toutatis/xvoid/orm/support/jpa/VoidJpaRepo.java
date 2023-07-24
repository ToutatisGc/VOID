package cn.toutatis.xvoid.orm.support.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * JPA仓库接口
 *
 * @author Toutatis_Gc
 */
@NoRepositoryBean
public interface VoidJpaRepo<T,ID> extends JpaRepository<T,ID>, JpaSpecificationExecutor<T> {

}
