package cn.toutatis.xvoid.orm.forum.persistence;

import cn.toutatis.xvoid.orm.forum.entity.ForumArticleCategory;
import cn.toutatis.xvoid.orm.forum.entity.projection.ForumArticleCategoryProjection;
import cn.toutatis.xvoid.orm.support.jpa.VoidJpaRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * <p>
 * 系统权限类 JPA Repository 接口
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-26
*/
public interface ForumArticleCategoryRepository extends VoidJpaRepo<ForumArticleCategory,Integer> {

    /**
     * 查询所有文章集合下的文章
     * @return 集合文章列表
     */
    @Query("""
            SELECT fac
            FROM ForumArticleCategory fac
            LEFT JOIN fac.categoryArticles cai
            LEFT JOIN cai.article
            WHERE fac.businessType = 'XVOID_FORUM'
            order by fac.categoryOrder DESC
            """)
    Page<ForumArticleCategoryProjection> findCategoryPage(Pageable pageable);



}
