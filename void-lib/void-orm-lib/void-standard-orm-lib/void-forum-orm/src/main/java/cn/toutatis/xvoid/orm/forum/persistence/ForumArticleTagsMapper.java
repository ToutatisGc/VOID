package cn.toutatis.xvoid.orm.forum.persistence;

import cn.toutatis.xvoid.orm.forum.entity.ForumArticleTags;
import cn.toutatis.xvoid.orm.forum.entity.intermediate.ForumArticleTagsIntermediate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * [论坛业务] 文章标签类 Mapper 接口
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-26
*/
@Mapper
@Repository
public interface ForumArticleTagsMapper extends BaseMapper<ForumArticleTags> {

    /**
     * 查找文章标签
     * @param articleId 文章ID
     * @return 文章所拥有的标签
     */
    @Select("SELECT * FROM "+ForumArticleTags.TABLE+" " +
            "WHERE id IN " +
            "(SELECT tagId FROM "+ForumArticleTagsIntermediate.TABLE+" WHERE articleId = #{articleId})")
    List<ForumArticleTags> selectArticleTags(@Param("articleId") Integer articleId);

}
