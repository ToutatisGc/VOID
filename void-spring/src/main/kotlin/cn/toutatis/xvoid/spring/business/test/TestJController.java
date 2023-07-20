package cn.toutatis.xvoid.spring.business.test;

import cn.toutatis.xvoid.orm.forum.entity.ForumArticleCategory;
import cn.toutatis.xvoid.orm.forum.persistence.ForumArticleCategoryRepository;
import cn.toutatis.xvoid.orm.forum.persistence.ForumArticleMapper;
import cn.toutatis.xvoid.orm.forum.persistence.ForumArticleRepository;
import cn.toutatis.xvoid.spring.annotations.application.VoidController;
import cn.toutatis.xvoid.spring.business.user.persistence.SystemAuthPathRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

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
    public void test1(){
//        ForumArticleCategory forumArticleCategory = new ForumArticleCategory();
//        forumArticleCategory.setName("测试节点-"+ RandomStringUtils.randomAlphabetic(3));
//        forumArticleCategoryRepository.save(forumArticleCategory);

//        List<ForumArticleCategory> allWithArticles = forumArticleCategoryRepository.findAllWithArticles();
//        System.err.println(allWithArticles);

        ForumArticleCategory byIdWithArticles = forumArticleCategoryRepository.findByIdWithArticles(10);
        System.err.println(byIdWithArticles.getCategoryArticles());

//        System.err.println(forumArticleMapper);
//        ForumArticle forumArticle = new ForumArticle();
//        forumArticle.setTitle("测试测试");
//        forumArticle.setContent("111111111111111111111111111111111");
//        forumArticleMapper.insert(forumArticle);
////        System.err.println(1/0);
////        System.err.println(systemAuthPathRepository.findAll());
////        System.err.println(forumArticleRepository.findAll());
    }

}
