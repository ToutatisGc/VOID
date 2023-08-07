package cn.toutatis.xvoid.orm.support.mybatisplus;

import cn.toutatis.xvoid.common.result.ProxyResult;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.orm.support.Condition;
import cn.toutatis.xvoid.spring.configure.system.VoidConfiguration;
import cn.toutatis.xvoid.toolkit.data.DataExportConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
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
        return false;
    }

    @Override
    public boolean tombstone(Serializable id) {
        return false;
    }

    @Override
    public boolean tombstone(Condition<T> condition) {
        return false;
    }

    @Override
    public T getOneObj(Object condition, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Condition<T> condition) {
        return null;
    }

    @Override
    public Page<T> pageList(PagingQuery pagingQuery, T t) {
        return null;
    }

    @Override
    public Page<T> pageList(PagingQuery pagingQuery, T t, String mchId) {
        return null;
    }

    @Override
    public void export(HttpServletResponse response, DataExportConfig dataExportConfig) {

    }

    @Override
    public ProxyResult selectExportStatus(HttpServletRequest request, HttpServletResponse response, DataExportConfig dataExportConfig) {
        return null;
    }
}
