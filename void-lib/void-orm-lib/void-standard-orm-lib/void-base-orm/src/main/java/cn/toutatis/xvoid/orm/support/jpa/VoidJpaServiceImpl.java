package cn.toutatis.xvoid.orm.support.jpa;

import cn.toutatis.xvoid.common.result.ProxyResult;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.orm.support.mybatisplus.PagingQuery;
import cn.toutatis.xvoid.toolkit.data.DataExportConfig;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Spring Data JPA 标准仓库
 * @param <R> 继承了JpaRepo的仓库类
 * @param <ID> ID类型
 * @param <T> 实体类
 * @author Toutatis_Gc
 */
public class VoidJpaServiceImpl<R extends VoidJpaRepo<T,ID>,ID,T extends EntityBasicAttribute<T>> implements VoidJpaService<T> {

    @Autowired
    protected R repository;


    @Override
    public boolean save(T entity) {
        repository.save(entity);
        return true;
    }

    @Override
    public boolean saveOrUpdate(T entity) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entity.getClass());
        Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
        String keyProperty = tableInfo.getKeyProperty();
        //TODO 完善
        return false;
    }

    @Override
    public boolean saveBatch(Collection<T> entityList) {
        return false;
    }

    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean removeById(Serializable id) {
        return false;
    }

    @Override
    public boolean removeByEntity(T entity) {
        return false;
    }

    @Override
    public boolean remove(Object condition) {
        return false;
    }

    @Override
    public boolean removeByIds(Collection<?> idList) {
        return false;
    }

    @Override
    public boolean tombstone(T entity) {
        return false;
    }

    @Override
    public boolean tombstone(Object condition) {
        return false;
    }

    @Override
    public boolean updateById(T entity) {
        return false;
    }

    @Override
    public boolean update(Object condition) {
        return false;
    }

    @Override
    public T getById(Serializable id) {
        return null;
    }

    @Override
    public T getOneObj(Object condition) {
        return null;
    }

    @Override
    public T getOneObj(Object condition, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Object condition) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getMapList(Object condition) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public long count(Object condition) {
        return 0;
    }

    @Override
    public List<T> list() {
        return null;
    }

    @Override
    public List<T> list(Object condition) {
        return null;
    }

    @Override
    public List<T> listByIds(Collection<? extends Serializable> idList) {
        return null;
    }

    @Override
    public Page<T> pageList(PagingQuery pagingQuery, T t) {
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
