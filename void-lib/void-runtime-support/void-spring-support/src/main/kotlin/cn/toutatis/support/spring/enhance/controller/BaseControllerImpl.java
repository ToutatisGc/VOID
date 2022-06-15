package cn.toutatis.support.spring.enhance.controller;

import cn.toutatis.common.standard.StandardFields;
import cn.toutatis.data.common.Actions;
import cn.toutatis.data.common.EntityBasicAttribute;
import cn.toutatis.data.common.ProxyResult;
import cn.toutatis.data.common.ResultCode;
import cn.toutatis.data.implement.Result;
import cn.toutatis.support.spring.config.VoidConfiguration;
import cn.toutatis.support.spring.config.orm.mybatisplus.support.CommonWrapper;
import cn.toutatis.support.spring.config.orm.mybatisplus.support.PagingQuery;
import cn.toutatis.support.spring.config.orm.mybatisplus.support.VService;
import cn.toutatis.toolkit.objects.ObjectToolkit;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Toutatis_Gc
 * @param <O> 实体类
 * @param <SERVICE>  继承了MP的service
 */
@Transactional(rollbackFor = Exception.class)
public class BaseControllerImpl<O extends EntityBasicAttribute<O>, SERVICE extends VService<O>> implements BaseController<O> {

    public Logger logger = LoggerFactory.getLogger(getClass());

    private final ObjectToolkit objectToolkit = ObjectToolkit.getInstance();

    @Autowired
    HttpServletRequest request;

    @Autowired
    public SERVICE service;

    @Autowired
    private VoidConfiguration voidConfiguration;

    private final Boolean platformMode = voidConfiguration.getPlatformMode();

    public CommonWrapper<O> commonWrapper = CommonWrapper.getInstance();

    public ProxyResult result = new ProxyResult(
            voidConfiguration.getGlobalServiceConfig().getUseDetailedMode(),
            voidConfiguration.getGlobalServiceConfig().getAutoConfig()
    );

    @Override
    @RequestMapping(value = "/getList",method = RequestMethod.POST)
    public Result getList(PagingQuery pagingQuery, O obj) {
        String mchId = request.getHeader(StandardFields.VOID_REQUEST_HEADER_MCH_ID);
        Page<O> page;
        if (platformMode && objectToolkit.strIsBlank(mchId)){
            return new ProxyResult(ResultCode.NOT_TENANT);
        }else if(!platformMode){
            page = service.getList(pagingQuery,obj);
        }else{
            page = service.getList(pagingQuery,obj,mchId);
        }
        Map<String,Object> objectMap = new HashMap<>(2);
        objectMap.put("list",page.getRecords());
        objectMap.put("totalCount",page.getTotal());
        result.setData(objectMap, Actions.SELECT);
        return result;
    }

    @Override
    public Result getById(O entity) {
        return null;
    }

    @Override
    public Result updateStatus(O entity) {
        return null;
    }

    @Override
    public Result batchDeleteReal(List<O> entities, String id) {
        return null;
    }

    @Override
    public Result tombstone(List<O> entities) {
        return null;
    }

    @Override
    public Result tombstoneOne(O entity) {
        return null;
    }

    @Override
    public Result saveRecord(O entity) {
        return null;
    }

    @Override
    public Result search(JSONObject object) {
        return null;
    }

    @Override
    public Result check(O entity, String remark) {
        return null;
    }
}
