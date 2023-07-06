package cn.toutatis.xvoid.spring.business.test.persistence;

import cn.toutatis.xvoid.bussiness.forum.entity.ForumArticle;
import cn.toutatis.xvoid.bussiness.forum.entity.ForumArticleCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ForumArticleCategoryMapper extends BaseMapper<ForumArticleCategory> {

}