package cn.toutatis.xvoid.spring.business.test;

import cn.toutatis.xvoid.bussiness.forum.entity.ForumArticle;
import cn.toutatis.xvoid.bussiness.forum.persistence.ForumArticleMapper;
import cn.toutatis.xvoid.bussiness.forum.persistence.ForumArticleRepository;
import cn.toutatis.xvoid.spring.business.user.persistence.SystemAuthPathRepository;
import cn.toutatis.xvoid.spring.annotations.application.VoidController;
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

    @RequestMapping(value = "/test1",method = RequestMethod.POST)
    public void test1(){
        System.err.println(forumArticleMapper);
        ForumArticle forumArticle = new ForumArticle();
        forumArticle.setTitle("测试测试");
        forumArticle.setContent("111111111111111111111111111111111");
        forumArticleMapper.insert(forumArticle);
//        System.err.println(1/0);
//        System.err.println(systemAuthPathRepository.findAll());
//        System.err.println(forumArticleRepository.findAll());
    }

}
