package cn.toutatis.xvoid.spring.business.test;

import cn.toutatis.xvoid.bussiness.forum.persistence.ForumArticleMapper;
import cn.toutatis.xvoid.bussiness.forum.ForumArticleRepository;
import cn.toutatis.xvoid.support.spring.amqp.persistence.SystemLogRepository;
import cn.toutatis.xvoid.support.spring.annotations.VoidController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@VoidController
@RequestMapping("/test")
public class TestJController {

    @Autowired
    private ForumArticleRepository forumArticleRepository;

    @Autowired
    private ForumArticleMapper forumArticleMapper;

    @Autowired
    private SystemLogRepository systemLogRepository;

    @RequestMapping(value = "/test1",method = RequestMethod.POST)
    public void test1(){
        System.err.println(forumArticleMapper);
        System.err.println(forumArticleRepository);
        System.err.println(systemLogRepository.findAll());
        System.err.println(forumArticleRepository.findAll());
    }

}
