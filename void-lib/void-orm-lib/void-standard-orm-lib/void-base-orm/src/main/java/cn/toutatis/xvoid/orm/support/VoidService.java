package cn.toutatis.xvoid.orm.support;

import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.orm.support.mybatisplus.PagingQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Toutatis
 * 继承抽象服务
 * @param <T> 实体类
 */
public interface VoidService<T extends EntityBasicAttribute<T>>{


    /**
     * 向表插入一条实体
     * @param entity 插入实体
     * @return 插入成功
     */
    boolean save(T entity);

    /**
     * TableId 注解存在更新记录，否插入一条记录
     * @param entity 实体对象
     * @return 保存或插入成功
     */
    @Transactional(rollbackFor = Exception.class)
    boolean saveOrUpdate(T entity);

    /**
     * 批量插入实体
     * @param entityList 实体集合
     * @return 插入成功
     */
    boolean saveBatch(Collection<T> entityList);

    /**
     * 分批次像数据库插入实体
     * @param entityList 实体集合
     * @param batchSize 批次大小
     * @return 插入成功
     */
    boolean saveBatch(Collection<T> entityList, int batchSize);


    /**
     * 批量修改插入
     * @param entityList 实体集合
     * @return 插入成功
     */
    @Transactional(rollbackFor = Exception.class)
    boolean saveOrUpdateBatch(Collection<T> entityList);

    /**
     * 批量修改插入
     *
     * @param entityList 实体集合
     * @param batchSize  批次大小
     * @return 插入或保存成功
     */
    @Transactional(rollbackFor = Exception.class)
    boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);

    /**
     * 根据实体主键ID删除记录
     * @param id 主键ID
     * @return 删除成功
     */
    @Transactional(rollbackFor = Exception.class)
    boolean removeById(Serializable id);

    /**
     * 根据实体主键ID删除记录
     * @param entity 实体类
     * @return 删除成功
     */
    @Transactional(rollbackFor = Exception.class)
    boolean removeByEntity(T entity);

    /**
     * 根据条件构造器删除记录
     * 需要根据不同的ORM框架进行派生
     * @param condition 条件构造器
     * @return 删除成功
     */
    @Transactional(rollbackFor = Exception.class)
    boolean remove(Object condition);

    /**
     * 根据ID列表删除实体
     * @param idList ID列表
     * @return 删除成功
     */
    @Transactional(rollbackFor = Exception.class)
    boolean removeByIds(Collection<?> idList);

    /**
     * 记录逻辑删除
     * 本身操作是更新dataStatus为逻辑删除状态
     * @see cn.toutatis.xvoid.common.result.DataStatus 数据状态
     * @see EntityBasicAttribute 逻辑删除字段
     * @param entity 实体
     * @return 逻辑删除成功
     */
    @Transactional(rollbackFor = Exception.class)
    boolean tombstone(T entity);

    /**
     * 记录逻辑删除
     * 本身操作是更新dataStatus为逻辑删除状态
     * @see cn.toutatis.xvoid.common.result.DataStatus 数据状态
     * @see EntityBasicAttribute 逻辑删除字段
     * @param condition 条件构造器
     * @return 逻辑删除成功
     */
    @Transactional(rollbackFor = Exception.class)
    boolean tombstone(Object condition);

    /**
     * 根据实体更新记录
     * 注意:更新时需要更新版本号
     * @see EntityBasicAttribute 版本号VERSION字段
     * @param entity 实体
     * @return 更新成功
     */
    @Transactional(rollbackFor = Exception.class)
    boolean updateById(T entity);


    /**
     * 根据条件构造器更新表
     * 注意:更新时需要更新版本号
     * @see EntityBasicAttribute 版本号VERSION字段
     * @param condition 条件构造器
     * @return 更新成功
     */
    @Transactional(rollbackFor = Exception.class)
    boolean update(Object condition);

    /**
     * 根据主键ID查询实体
     * @param id 主键ID
     * @return 实体
     */
    T getById(Serializable id);


    /**
     * 根据条件构造器查询单个实体
     * @param condition 条件构造器
     * @return 实体
     */
    T getOneObj(Object condition);

    /**
     * 根据条件构造器查询单个实体
     * @param condition 条件构造器
     * @param throwEx 是否抛出异常
     * @return 实体
     */
    T getOneObj(Object condition, boolean throwEx);

    /**
     * 不指定实体类,查询Map映射
     * @param condition 条件构造器
     * @return MAP数据
     */
    Map<String, Object> getMap(Object condition);

    /**
     * 查询List映射
     * @param condition 条件构造器
     * @return List映射
     */
    List<Map<String,Object>> getMapList(Object condition);

    /**
     * 查询表记录总数
     * @return 表记录总数
     */
    long count();

    /**
     * 查询表记录数
     * @param condition 条件构造器
     * @return 条件限定表记录数
     */
    long count(Object condition);

    /**
     * 查询所有记录
     * @return 所有记录
     */
    List<T> list();

    /**
     * 根据条件构造器查询列表
     * @param condition 条件构造器
     * @return 记录列表
     */
    List<T> list(Object condition);

    /**
     * 根据ID列表查询查询实体集合
     * @param idList ID列表
     * @return 实体集合
     */
    List<T> listByIds(Collection<? extends Serializable> idList);

    /**
     * 获取分页列表
     * @param pagingQuery 分页对象
     * @param t 实体类
     * @return 分页结果
     */
    Page<T> pageList(PagingQuery pagingQuery, T t);

    // TODO 导出表格数据
//    /**
//     * 导出表格数据
//     * @param response 响应对象
//     * @param dataExportConfig 导出配置
//     */
//    void export(HttpServletResponse response, DataExportConfig dataExportConfig);
//
//    /**
//     * 获取导出状态
//     * @param request 请求
//     * @param response 响应
//     * @param dataExportConfig 导出配置
//     */
//    ProxyResult selectExportStatus(HttpServletRequest request, HttpServletResponse response, DataExportConfig dataExportConfig);

}
