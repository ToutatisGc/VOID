package cn.toutatis.xvoid.support.spring.enhance.controller;

import cn.toutatis.common.standard.StandardFields;
import cn.toutatis.data.common.result.Actions;
import cn.toutatis.data.common.EntityBasicAttribute;
import cn.toutatis.data.common.result.ProxyResult;
import cn.toutatis.data.common.result.ResultCode;
import cn.toutatis.data.common.result.Result;
import cn.toutatis.xvoid.toolkit.objects.ObjectToolkit;
import cn.toutatis.xvoid.support.spring.config.VoidConfiguration;
import cn.toutatis.xvoid.support.spring.config.orm.mybatisplus.support.CommonWrapper;
import cn.toutatis.xvoid.support.spring.config.orm.mybatisplus.support.PagingQuery;
import cn.toutatis.xvoid.support.spring.config.orm.mybatisplus.support.VoidMybatisService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Toutatis_Gc
 * @param <O> 实体类
 * @param <SERVICE>  继承了MP的service
 */
@Transactional(rollbackFor = Exception.class)
public class BaseControllerImpl<O extends EntityBasicAttribute<O>, SERVICE extends VoidMybatisService<O>> implements BaseController<O> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ObjectToolkit objectToolkit = ObjectToolkit.getInstance();

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SERVICE service;

    @Autowired
    private VoidConfiguration voidConfiguration;

    private Boolean platformMode;

    private CommonWrapper<O> commonWrapper = CommonWrapper.getInstance();

    private ProxyResult result;

    public BaseControllerImpl(VoidConfiguration voidConfiguration) {
        this.voidConfiguration = voidConfiguration;
        platformMode = voidConfiguration.getPlatformMode();
        result = new ProxyResult(
                voidConfiguration.getGlobalServiceConfig().getUseDetailedMode(),
                voidConfiguration.getGlobalServiceConfig().getAutoConfig()
        );
    }

    @Override
    @ApiOperation("获取实体列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pagingQuery",value="分页对象",required=false,paramType="query",dataTypeClass = PagingQuery.class),
            @ApiImplicitParam(name="obj",value="操作对象",required=false,paramType="query",dataTypeClass = Object.class)
    })
    @RequestMapping(value = "/getList",method = RequestMethod.POST)
    public Result getList(@RequestParam(required = false) PagingQuery pagingQuery,
                          @RequestParam(required = false) O obj
    ) {
        if (pagingQuery == null){ pagingQuery = new PagingQuery(); }
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

    @ApiIgnore
    @Override
    @RequestMapping(value = "/getById",method = RequestMethod.POST)
    public Result getById(O entity) {
        if (entity == null){ return new ProxyResult(ResultCode.NOT_HAVE_SELECT_DATA); }
        QueryWrapper<O> oQueryWrapper = new QueryWrapper<>(entity);
        if (platformMode){
            String mchId = request.getHeader(StandardFields.VOID_REQUEST_HEADER_MCH_ID);
            if (objectToolkit.strNotBlank(mchId)){
                oQueryWrapper.eq("mchId",mchId);
            }else {
                return new ProxyResult(ResultCode.NOT_TENANT);
            }
        }
        O one = service.getOne(oQueryWrapper);
        result.setData(one,Actions.SELECT);
        return result;
    }

//    @Override
//    public Result updateStatus(@RequestParam O entity) {
//        return null;
//    }
//
//    @Override
//    public Result batchDeleteReal(@RequestParam List<O> entities,@RequestParam String id) {
//        return null;
//    }
//
//    @Override
//    public Result tombstone(@RequestParam List<O> entities) {
//        return null;
//    }
//
//    @Override
//    public Result tombstoneOne(@RequestParam O entity) {
//        return null;
//    }
//
//    @Override
//    public Result saveRecord(@RequestParam O entity) {
//        return null;
//    }
//
//    @Override
//    public Result search(@RequestParam JSONObject object) {
//        return null;
//    }
//
//    @Override
//    public Result check(@RequestParam O entity,@RequestParam String remark) {
//        return null;
//    }
}
