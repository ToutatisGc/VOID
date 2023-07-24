package cn.toutatis.xvoid.orm.forum.services.impl;

import cn.toutatis.xvoid.orm.forum.entity.ForumArticleCategory;
import cn.toutatis.xvoid.orm.forum.persistence.ForumArticleCategoryRepository;
import cn.toutatis.xvoid.orm.forum.services.ForumArticleCategoryService;
import cn.toutatis.xvoid.orm.support.jpa.VoidJpaServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Toutatis_Gc
 */
@Service
public class ForumArticleCategoryServiceImpl
        extends VoidJpaServiceImpl<ForumArticleCategoryRepository,Integer,ForumArticleCategory>
        implements ForumArticleCategoryService {

}
