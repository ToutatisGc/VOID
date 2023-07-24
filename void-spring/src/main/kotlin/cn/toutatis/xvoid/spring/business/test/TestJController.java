package cn.toutatis.xvoid.spring.business.test;

import cn.toutatis.xvoid.orm.base.data.common.result.ProxyResult;
import cn.toutatis.xvoid.orm.forum.entity.ForumArticle;
import cn.toutatis.xvoid.orm.forum.persistence.ForumArticleCategoryRepository;
import cn.toutatis.xvoid.orm.forum.persistence.ForumArticleMapper;
import cn.toutatis.xvoid.orm.forum.persistence.ForumArticleRepository;
import cn.toutatis.xvoid.spring.annotations.application.VoidController;
import cn.toutatis.xvoid.spring.business.user.persistence.SystemAuthPathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@VoidController
@RequestMapping("/test")
public class TestJController {

    @Autowired
    private ForumArticleMapper forumArticleMapper;

    @Autowired
    private SystemAuthPathRepository systemAuthPathRepository;

    @Autowired
    private ForumArticleRepository forumArticleRepository;

    @Autowired
    private ForumArticleCategoryRepository forumArticleCategoryRepository;

    @RequestMapping(value = "/test1",method = RequestMethod.POST)
    public Object test1(){
//        PageRequest pageRequest = PageRequest.of(0, 15);
//        Page<ForumArticleCategoryProjection> categoryPage = forumArticleCategoryRepository.findCategoryPage(pageRequest);
//        List<ForumArticleCategoryProjection> forumArticleCategoryProjections = categoryPage.stream().toList();
////        for (ForumArticleCategoryProjection forumArticleCategoryProjection : forumArticleCategoryProjections) {
////            System.err.println(forumArticleCategoryProjection.getName());
////            System.err.println(forumArticleCategoryProjection.getCategoryArticles());
////        }
//        System.err.println(forumArticleCategoryProjections);

        ForumArticle article = forumArticleMapper.findArticle(1);
        return new ProxyResult(article);
    }

}
