package cn.toutatis.xvoid.orm.forum.persistence;

import cn.toutatis.xvoid.orm.forum.entity.ForumArticleCategory;
import cn.toutatis.xvoid.orm.forum.entity.projection.ForumArticleCategoryProjection;
import cn.toutatis.xvoid.orm.support.jpa.VoidJpaRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * <p>
 * 系统权限类 JPA Repository 接口
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-26
*/
public interface ForumArticleCategoryRepository extends VoidJpaRepo<ForumArticleCategory,Integer> {


    @EntityGraph(value = "ForumArticleCategory.Graph", type = EntityGraph.EntityGraphType.LOAD)
    @Override
    List<ForumArticleCategory> findAll();
}
