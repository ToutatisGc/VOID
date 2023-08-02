package cn.toutatis.xvoid.orm.support.mybatisplus;

import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.common.result.DataStatus;
import cn.toutatis.xvoid.orm.support.Condition;
import cn.toutatis.xvoid.orm.support.VoidService;
import cn.toutatis.xvoid.toolkit.data.DataExportConfig;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Toutatis_Gc
 */
public interface VoidMybatisService<T extends EntityBasicAttribute<T>> extends VoidService<T>,IService<T> {

    /**
     * 向表插入一条实体
     *
     * @param entity 插入实体
     * @return 插入成功
     */
    @Override
    default boolean save(T entity){return IService.super.save(entity);};

    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     * @return 保存或插入成功
     */
    @Override
    boolean saveOrUpdate(T entity);

    /**
     * 批量插入实体
     *
     * @param entityList 实体集合
     * @return 插入成功
     */
    @Override
    boolean saveBatch(Collection<T> entityList);

    /**
     * 分批次像数据库插入实体
     *
     * @param entityList 实体集合
     * @param batchSize  批次大小
     * @return 插入成功
     */
    @Override
    boolean saveBatch(Collection<T> entityList, int batchSize);

    /**
     * 批量修改插入
     *
     * @param entityList 实体集合
     * @return 插入成功
     */
    @Override
    boolean saveOrUpdateBatch(Collection<T> entityList);

    /**
     * 批量修改插入
     *
     * @param entityList 实体集合
     * @param batchSize  批次大小
     * @return 插入或保存成功
     */
    @Override
    boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);

    /**
     * 根据实体主键ID删除记录
     *
     * @param id 主键ID
     * @return 删除成功
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 根据实体主键ID删除记录
     *
     * @param entity 实体类
     * @return 删除成功
     */
    @Override
    boolean removeByEntity(T entity);

    /**
     * 根据条件构造器删除记录
     * 需要根据不同的ORM框架进行派生
     *
     * @param condition 条件构造器
     * @return 删除成功
     */
    @Override
    default boolean remove(Condition<T> condition){
        Wrapper<T> wrapper = (Wrapper<T>) condition;
        return IService.super.remove(wrapper);
    };

    /**
     * 根据ID列表删除实体
     *
     * @param idList ID列表
     * @return 删除成功
     */
    @Override
    boolean removeByIds(Collection<?> idList);

    /**
     * 记录逻辑删除
     * 本身操作是更新dataStatus为逻辑删除状态
     *
     * @param entity 实体
     * @return 逻辑删除成功
     * @see DataStatus 数据状态
     * @see EntityBasicAttribute 逻辑删除字段
     */
    @Override
    boolean tombstone(T entity);

    /**
     * 记录逻辑删除
     * 本身操作是更新dataStatus为逻辑删除状态
     *
     * @param id 实体ID
     * @return 逻辑删除成功
     * @see DataStatus 数据状态
     * @see EntityBasicAttribute 逻辑删除字段
     */
    @Override
    boolean tombstone(Serializable id);

    /**
     * 根据实体更新记录
     * 注意:更新时需要更新版本号
     *
     * @param entity 实体
     * @return 更新成功
     * @see EntityBasicAttribute 版本号VERSION字段
     */
    @Override
    boolean updateById(T entity);

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     * @param batchSize  更新批次数量
     */
    @Override
    boolean updateBatchById(Collection<T> entityList, int batchSize);

    /**
     * 根据主键ID查询实体
     *
     * @param id 主键ID
     * @return 实体
     */
    @Override
    T getById(Serializable id);

    /**
     * 查询表记录总数
     *
     * @return 表记录总数
     */
    @Override
    long count();

    /**
     * 查询所有记录
     *
     * @return 所有记录
     */
    @Override
    List<T> list();

    /**
     * 获取对应 entity 的 BaseMapper
     *
     * @return BaseMapper
     */
    @Override
    BaseMapper<T> getBaseMapper();

    /**
     * 获取 entity 的 class
     *
     * @return {@link Class<T>}
     */
    @Override
    Class<T> getEntityClass();

    /**
     * 根据ID列表查询查询实体集合
     *
     * @param idList ID列表
     * @return 实体集合
     */
    @Override
    List<T> listByIds(Collection<? extends Serializable> idList);

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link QueryWrapper}
     * @param throwEx      有多个 result 是否抛出异常
     */
    @Override
    T getOne(Wrapper<T> queryWrapper, boolean throwEx);

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link QueryWrapper}
     */
    @Override
    Map<String, Object> getMap(Wrapper<T> queryWrapper);


    /**
     * 获取分页列表
     *
     * @param pagingQuery 分页对象
     * @param t           实体类
     * @return 分页结果
     */
    @Override
    Page<T> pageList(PagingQuery pagingQuery, T t);

    /**
     * 获取分页列表
     *
     * @param pagingQuery 分页对象
     * @param t           实体类
     * @param mchId       多租户ID
     * @return 分页结果
     */
    @Override
    Page<T> pageList(PagingQuery pagingQuery, T t, String mchId);

    /**
     * 导出表格数据
     *
     * @param response         响应对象
     * @param dataExportConfig 导出配置
     */
    @Override
    void export(HttpServletResponse response, DataExportConfig dataExportConfig);

    /**
     * 获取导出状态
     *
     * @param request          请求
     * @param response         响应
     * @param dataExportConfig 导出配置
     */
    @Override
    void selectExportStatus(HttpServletRequest request, HttpServletResponse response, DataExportConfig dataExportConfig);
}
