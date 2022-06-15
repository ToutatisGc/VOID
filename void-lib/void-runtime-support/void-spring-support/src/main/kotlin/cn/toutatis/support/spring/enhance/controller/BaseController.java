package cn.toutatis.support.spring.enhance.controller;

import cn.toutatis.data.implement.Result;
import cn.toutatis.support.spring.config.orm.mybatisplus.support.PagingQuery;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author Toutatis_Gc
 * 每个控制器必须要有的实现方法
 */
public interface BaseController<O> {


    /**
     * @param pagingQuery 分页实体
     * @param obj 判断条件
     * @return 实体List
     */
    Result getList(PagingQuery pagingQuery, O obj);

    /**
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
    Result batchDeleteReal(List<O> entities,String id);

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
