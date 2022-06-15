package cn.toutatis.support.spring.config.orm.mybatisplus.support;

import cn.toutatis.data.common.result.DataStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Toutatis_Gc
 * @param <M> 继承MybatisPlus的实体类
 * @param <T> 实体类
 */
public class VServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements VService<T> {

    public CommonWrapper commonWrapper = CommonWrapper.getInstance();

    @Autowired
    protected M baseMapper;

    @Override
    public Page<T> getList(PagingQuery pagingQuery, T obj) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(obj);
        queryWrapper.orderByDesc("createTime");
        queryWrapper.eq("status", DataStatus.SYS_OPEN_0000.getIndex());
        baseMapper.selectList(queryWrapper);
        Page<T> page = new Page<>(pagingQuery.getCurrentPage(), pagingQuery.getPageSize());
        page = baseMapper.selectPage(page, queryWrapper);
        return page;
    }

    @Override
    public Page<T> getList(PagingQuery pagingQuery, T obj, String mchId) {
        if (mchId == null || mchId.length() == 0) {
            return this.getList(pagingQuery, obj);
        } else {
            QueryWrapper<T> queryWrapper = new QueryWrapper<>(obj);
            queryWrapper.orderByDesc("createTime");
            queryWrapper.eq("status", DataStatus.SYS_OPEN_0000.getIndex());
            queryWrapper.eq("mchId", mchId);
            baseMapper.selectList(queryWrapper);
            Page<T> page = new Page<>(pagingQuery.getCurrentPage(), pagingQuery.getPageSize());
            page = baseMapper.selectPage(page, queryWrapper);
            return page;
        }
    }
}
