package cn.toutatis.xvoid.spring.support.enhance.controller;

import cn.toutatis.xvoid.common.result.Result;
import cn.toutatis.xvoid.orm.support.mybatisplus.PagingQuery;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author Toutatis_Gc
 * 每个控制器必须要有的实现方法
 */
public interface BaseController<O> {

    /**
     * 获取表中所有记录
     * @return 所有列表
     */
    Result getList();

    /**
     * 获取分页数据
     * @param pagingQuery 分页实体
     * @param obj 判断条件
     * @return 实体List
     */
    Result page(PagingQuery pagingQuery, O obj);

    /**
     * 根据ID获取实体
     * @param entity id 前端传入ID
     * @return 根据ID查找该实体
     */
    Result getById(O entity);

    /**
     * @param entity 更新实体的状态
     * @return 更新结果
     */
    Result updateStatus(O entity);

    /**
     * @param entities 批量删除实体
     * @return 删除成功标志
     */
    Result batchDeleteReal(List<O> entities, String id);

    /**
     * @param entities 逻辑删除
     * @return 删除结果
     */
    Result tombstone(List<O> entities);

    /**
     * @param entity 单个实体逻辑删除
     * @return 删除结果
     */
    Result tombstoneOne(O entity);

    /**
     * @param entity 要保存的实体
     * @return 保存结果
     */
    Result saveRecord(O entity);

    /**
     * @param object 查询实体
     * @return 查询结果
     * 查询应该为模糊查询
     */
    Result search(JSONObject object);


    /**
     * @param entity 审核实体
     * @param remark 备注
     * @return 审核结果
     */
    Result check(O entity,String remark);
}
