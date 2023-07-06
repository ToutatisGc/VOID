package cn.toutatis.xvoid.spring.business.test.persistence;

import cn.toutatis.xvoid.bussiness.forum.entity.ForumArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ForumArticleMapper extends BaseMapper<ForumArticle> {

}