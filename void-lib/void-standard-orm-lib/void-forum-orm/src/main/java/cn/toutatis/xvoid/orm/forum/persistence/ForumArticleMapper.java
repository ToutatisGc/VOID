package cn.toutatis.xvoid.orm.forum.persistence;

import cn.toutatis.xvoid.orm.forum.entity.ForumArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

}
