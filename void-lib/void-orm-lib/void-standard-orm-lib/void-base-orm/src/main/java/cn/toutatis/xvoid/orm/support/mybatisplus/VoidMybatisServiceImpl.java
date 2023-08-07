package cn.toutatis.xvoid.orm.support.mybatisplus;

import cn.toutatis.xvoid.common.Meta;
import cn.toutatis.xvoid.common.result.DataStatus;
import cn.toutatis.xvoid.common.result.ProxyResult;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.orm.support.Condition;
import cn.toutatis.xvoid.spring.configure.system.VoidConfiguration;
import cn.toutatis.xvoid.toolkit.data.DataExportConfig;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
    public boolean tombstone(T entity) {
        entity.setLogicDeleted(DataStatus.SYS_DELETED_0000);
        return this.updateById(entity);
    }

    @Override
    public boolean tombstone(Object condition) {
        this.checkConditionIsWrapper(condition);
        UpdateWrapper<T> wrapper = (UpdateWrapper<T>) condition;
        wrapper.set("logicDeleted", DataStatus.SYS_DELETED_0000);
        return this.update(wrapper);
    }

    @Override
    public T getOneObj(Object condition, boolean throwEx) {
        this.checkConditionIsWrapper(condition);
        return this.getOne((Wrapper<T>) condition,throwEx);
    }

    @Override
    public Map<String, Object> getMap(Object condition) {
        this.checkConditionIsWrapper(condition);
        return this.getMap((Wrapper<T>) condition);
    }

    @Override
    public Page<T> pageList(PagingQuery pagingQuery, T t) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(t);
        queryWrapper.orderBy(true, pagingQuery.getAsc(), pagingQuery.getOrderByColumn());
        Page<T> page  = new Page<>(pagingQuery.getCurrentPage(),pagingQuery.getPageSize());
        page = baseMapper.selectPage(page, queryWrapper);
        return page;
    }

    @Override
    public void export(HttpServletResponse response, DataExportConfig dataExportConfig) {

    }

    @Override
    public ProxyResult selectExportStatus(HttpServletRequest request, HttpServletResponse response, DataExportConfig dataExportConfig) {
        return null;
    }

    private void checkConditionIsWrapper(Object condition){
        if (!(condition instanceof Wrapper)){
            throw new IllegalArgumentException(
                    LoggerToolkit.infoWithModule(Meta.MODULE_NAME, "ORM", "条件构造器请使用Wrapper")
            );
        }
    }


}
