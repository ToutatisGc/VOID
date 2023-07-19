package cn.toutatis.xvoid.orm.forum.persistence;

import cn.toutatis.xvoid.orm.forum.entity.ForumArticle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * 系统权限类 JPA Repository 接口
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-26
*/
public interface ForumArticleCategoryRepository extends JpaRepository<ForumArticle,Integer> {

}
