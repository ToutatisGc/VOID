package cn.toutatis.xvoid.orm.forum.entity.projection;

import java.util.List;

public interface ForumArticleCategoryProjection {

    Integer getId();

    String getName();

    List<ForumArticleProjection> getCategoryArticles();

}
