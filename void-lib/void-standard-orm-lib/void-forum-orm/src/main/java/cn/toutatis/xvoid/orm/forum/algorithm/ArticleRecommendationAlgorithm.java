package cn.toutatis.xvoid.orm.forum.algorithm;

import cn.toutatis.xvoid.orm.forum.entity.ForumArticle;
import lombok.Setter;

/**
 * 文章推荐算法
 * @author Toutatis_Gc
 */
@Setter
public class ArticleRecommendationAlgorithm {

    private static Integer TOP_WEIGHT = Integer.MAX_VALUE;

    public static Integer defaultWeightedAlgorithm(ForumArticle forumArticle){
        Integer score = 0;

        return 0;
    }

}
