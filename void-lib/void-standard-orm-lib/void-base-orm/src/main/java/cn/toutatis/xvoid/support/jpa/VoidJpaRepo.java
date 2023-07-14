package cn.toutatis.xvoid.support.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * JPA仓库接口
 *
 * @author Toutatis_Gc
 */
public interface VoidJpaRepo<T,ID> extends JpaRepository<T,ID> {
}
