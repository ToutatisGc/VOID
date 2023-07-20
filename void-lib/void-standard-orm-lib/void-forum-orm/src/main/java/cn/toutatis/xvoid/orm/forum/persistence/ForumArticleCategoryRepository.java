package cn.toutatis.xvoid.orm.forum.persistence;

import cn.toutatis.xvoid.orm.forum.entity.ForumArticleCategory;
import cn.toutatis.xvoid.orm.support.jpa.VoidJpaRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    /**
     * 查询所有文章集合下的文章
     * @return 集合文章列表
     */
    @Query("SELECT DISTINCT fac FROM ForumArticleCategory fac " +
            "LEFT JOIN FETCH fac.categoryArticles cai " +
            "LEFT JOIN FETCH cai.article")
    List<ForumArticleCategory> findAllWithArticles();

    /**
     * 查询置顶ID文章集合下的文章
     * @param categoryId 集合ID
     * @return 集合文章列表
     */
    @Query("SELECT DISTINCT fac FROM ForumArticleCategory fac " +
            "LEFT JOIN FETCH fac.categoryArticles cai " +
            "LEFT JOIN FETCH cai.article " +
            "WHERE fac.id = :categoryId")
    ForumArticleCategory findByIdWithArticles(@Param("categoryId") Integer categoryId);

}
