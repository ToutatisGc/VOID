package cn.toutatis.support.spring.config.orm.mybatisplus.support;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Toutatis
 * 继承抽象服务
 * @param <T> 实体类
 */
public interface VService<T> extends IService<T> {

    /**
     * @param pagingQuery 分页对象
     * @param t 实体类
     * @return 分页结果
     */
    Page<T> getList(PagingQuery pagingQuery,T t);

    /**
     * @param pagingQuery 分页对象
     * @param t 实体类
     * @param mchId 多租户ID
     * @return 分页结果
     */
    Page<T> getList(PagingQuery pagingQuery,T t,String mchId);

}
