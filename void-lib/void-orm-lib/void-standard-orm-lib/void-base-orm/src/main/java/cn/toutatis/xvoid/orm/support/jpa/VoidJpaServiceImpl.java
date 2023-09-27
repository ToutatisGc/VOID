package cn.toutatis.xvoid.orm.support.jpa;

import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Spring Data JPA 标准仓库
 * @param <R> 继承了JpaRepo的仓库类
 * @param <ID> ID类型
 * @param <T> 实体类
 * @author Toutatis_Gc
 */
public class VoidJpaServiceImpl<R extends VoidJpaRepo<T,ID>,ID,T extends EntityBasicAttribute<T>>{

    @Autowired
    protected R repository;



}
