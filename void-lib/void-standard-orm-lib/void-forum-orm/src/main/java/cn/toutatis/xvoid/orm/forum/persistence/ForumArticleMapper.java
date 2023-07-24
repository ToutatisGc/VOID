package cn.toutatis.xvoid.orm.forum.persistence;

import cn.toutatis.xvoid.orm.forum.entity.ForumArticle;
import cn.toutatis.xvoid.orm.forum.entity.intermediate.ForumArticleTagsIntermediate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 论坛业务 文章类 Mapper 接口
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-26
*/
@Mapper
@Repository
public interface ForumArticleMapper extends BaseMapper<ForumArticle> {

    /**
     * 根据文章ID查找文章
     * @param articleId 文章ID
     * @return 符合条件的文章
     */
    @Select("SELECT * FROM "+ForumArticle.TABLE+" WHERE status = 0 AND id = #{articleId}")
    @Results({
            @Result(property = "forumArticleTagsIntermediates", column = "id",
                    many = @Many(select = "cn.toutatis.xvoid.orm.forum.persistence.ForumArticleMapper.selectTagsIntermediate")),
            @Result(property = "tags", column = "id",
                    many = @Many(select = "cn.toutatis.xvoid.orm.forum.persistence.ForumArticleTagsMapper.selectArticleTags"))
    })
    ForumArticle findArticle(@Param("articleId") Integer articleId);

    /**
     * 查询文章和标签关系
     * @param articleId 文章ID
     * @return 文章和标签关系
     */
    @Select("SELECT * FROM "+ForumArticleTagsIntermediate.TABLE+" WHERE status = 0 AND articleId = #{articleId}")
    List<ForumArticleTagsIntermediate> selectTagsIntermediate(@Param("articleId") Integer articleId);

}
