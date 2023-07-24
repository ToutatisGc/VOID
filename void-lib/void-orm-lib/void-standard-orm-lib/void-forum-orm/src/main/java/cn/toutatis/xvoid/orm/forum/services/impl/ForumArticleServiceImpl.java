package cn.toutatis.xvoid.orm.forum.services.impl;

import cn.toutatis.xvoid.orm.forum.entity.ForumArticle;
import cn.toutatis.xvoid.orm.forum.persistence.ForumArticleMapper;
import cn.toutatis.xvoid.orm.forum.services.ForumArticleService;
import cn.toutatis.xvoid.orm.support.mybatisplus.VoidMybatisServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Toutatis_Gc
 */
@Service
public class ForumArticleServiceImpl extends VoidMybatisServiceImpl<ForumArticleMapper,ForumArticle> implements ForumArticleService {

    @Override
    public List<ForumArticle> selectForumArticleListByUser(String userId) {
        QueryWrapper<ForumArticle> openByIdWrapper = wrapper.getOpenByIdWrapper(userId);
        return mapper.selectList(openByIdWrapper);
    }
}
