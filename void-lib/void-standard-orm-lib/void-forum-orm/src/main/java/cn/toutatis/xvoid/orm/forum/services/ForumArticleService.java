package cn.toutatis.xvoid.orm.forum.services;

import cn.toutatis.xvoid.orm.forum.entity.ForumArticle;
import cn.toutatis.xvoid.orm.forum.entity.ForumArticleCategory;
import cn.toutatis.xvoid.orm.support.VoidService;

import java.util.List;

/**
 * 论坛业务集 文章服务类
 * @author Toutatis_Gc
 */
public interface ForumArticleService extends VoidService<ForumArticle> {


    /**
     * 根据用户ID查询用户下的文章
     * @param userId 用户ID
     * @return 用户下的文章
     */
    List<ForumArticle> selectForumArticleListByUser(String userId);

//    List<ForumArticle> select

}
