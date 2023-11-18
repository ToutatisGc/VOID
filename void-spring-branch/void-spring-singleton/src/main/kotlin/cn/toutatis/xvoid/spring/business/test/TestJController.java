package cn.toutatis.xvoid.spring.business.test;

import cn.toutatis.xvoid.orm.base.authentication.entity.SystemAuthRole;
import cn.toutatis.xvoid.orm.base.authentication.entity.SystemUserLogin;
import cn.toutatis.xvoid.orm.base.authentication.persistence.SystemAuthPathRepository;
import cn.toutatis.xvoid.orm.base.authentication.persistence.SystemUserLoginRepository;
import cn.toutatis.xvoid.orm.base.authentication.service.SystemUserLoginService;
import cn.toutatis.xvoid.orm.forum.entity.ForumArticle;
import cn.toutatis.xvoid.orm.forum.persistence.ForumArticleCategoryRepository;
import cn.toutatis.xvoid.orm.forum.persistence.ForumArticleMapper;
import cn.toutatis.xvoid.orm.forum.persistence.ForumArticleRepository;
import cn.toutatis.xvoid.orm.forum.services.ForumArticleService;
import cn.toutatis.xvoid.spring.annotations.application.VoidController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    private ForumArticleService forumArticleService;

    @RequestMapping(value = "/test1",method = RequestMethod.POST)
    public Object test1(){
        long count = forumArticleService.count();
        System.err.println(count);
        QueryWrapper<ForumArticle> forumArticleQueryWrapper = new QueryWrapper<>();
        forumArticleQueryWrapper.eq("id","1");
//        ForumArticle oneObj = forumArticleService.getOneObj(forumArticleQueryWrapper);
        UpdateWrapper<ForumArticle> forumArticleUpdateWrapper = new UpdateWrapper<>();
        forumArticleUpdateWrapper.eq("id",1);
        boolean tombstone = forumArticleService.tombstone(forumArticleUpdateWrapper);
        System.err.println(tombstone);
        return null;
    }

    @Autowired
    private SystemUserLoginService systemUserLoginService;

    @Autowired
    private SystemUserLoginRepository userLoginRepository;

    @RequestMapping(value = "/test2",method = RequestMethod.GET)
    public SystemUserLogin test2(){
//        SystemUserLogin userLogin = systemUserLoginService.getById("00000000000000000000000000000000");
////        System.err.println(userLogin);
//        Set<SystemAuthRole> userRoles = userLogin.getUserRoles();
//        System.err.println(userRoles);
        Optional<SystemUserLogin> userLogin1 = userLoginRepository.findById("00000000000000000000000000000000");
        SystemUserLogin login = userLogin1.get();
        System.err.println(login);
        System.err.println("================================================");
        List<SystemAuthRole> userRoles = login.getUserRoles();
        System.err.println(userRoles);
        System.err.println("================================================");
        for (SystemAuthRole userRole : userRoles) {
            System.err.println(userRole.getAuthPaths());
        }
        return login;
//        userLoginRepository.delete(login);
//        System.err.println(login.getUserRoles());
    }

}
