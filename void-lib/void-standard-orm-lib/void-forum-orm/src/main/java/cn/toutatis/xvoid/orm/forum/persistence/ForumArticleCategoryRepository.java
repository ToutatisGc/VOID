package cn.toutatis.xvoid.orm.forum.persistence;

import cn.toutatis.xvoid.orm.forum.entity.ForumArticleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
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
public interface ForumArticleCategoryRepository extends JpaRepository<ForumArticleCategory,Integer> {

    @Query("SELECT DISTINCT fac FROM ForumArticleCategory fac " +
            "LEFT JOIN FETCH fac.categoryArticles cai " +
            "LEFT JOIN FETCH cai.article")
    List<ForumArticleCategory> findAllWithArticles();

}
