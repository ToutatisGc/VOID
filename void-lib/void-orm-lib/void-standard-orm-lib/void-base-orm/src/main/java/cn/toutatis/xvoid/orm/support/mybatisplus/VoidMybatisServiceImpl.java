package cn.toutatis.xvoid.orm.support.mybatisplus;

import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.orm.base.data.common.result.DataStatus;
import cn.toutatis.xvoid.common.enums.sheet.SheetExportType;
import cn.toutatis.xvoid.spring.configure.system.VoidConfiguration;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 增强mpp的service
 * @author Toutatis_Gc
 * @param <M> 继承MybatisPlus的Mapper
 * @param <T> 继承了EntityBasicAttribute的实体类
 */
public class VoidMybatisServiceImpl<M extends BaseMapper<T>, T extends EntityBasicAttribute<T>> extends ServiceImpl<M, T> implements VoidMybatisService<T> {

    public CommonWrapper<T> wrapper = new CommonWrapper<>();

    @Autowired
    protected M mapper;

    @Autowired
    protected VoidConfiguration config;

    @Override
    public Page<T> getList(PagingQuery pagingQuery, T obj) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(obj);
        queryWrapper.orderByDesc("createTime");
        queryWrapper.eq("status", DataStatus.SYS_OPEN_0000.getIndex());
        mapper.selectList(queryWrapper);
        Page<T> page = new Page<>(pagingQuery.getCurrentPage(), pagingQuery.getPageSize());
        page = mapper.selectPage(page, queryWrapper);
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
            mapper.selectList(queryWrapper);
            Page<T> page = new Page<>(pagingQuery.getCurrentPage(), pagingQuery.getPageSize());
            page = mapper.selectPage(page, queryWrapper);
            return page;
        }
    }

    @Override
    public void export(HttpServletResponse response, SheetExportType sheetExportType, List<T> data, String fileName) {

    }

    @Override
    public T getOne(QueryWrapper<T> queryWrapper) {
        return null;
    }


}
