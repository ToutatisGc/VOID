package cn.toutatis.xvoid.spring.support.enhance.controller;

import cn.toutatis.xvoid.common.standard.StandardFields;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.common.result.Actions;
import cn.toutatis.xvoid.common.result.ProxyResult;
import cn.toutatis.xvoid.common.result.Result;
import cn.toutatis.xvoid.common.result.ResultCode;
import cn.toutatis.xvoid.orm.support.Condition;
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration;
import cn.toutatis.xvoid.orm.support.mybatisplus.PagingQuery;
import cn.toutatis.xvoid.orm.support.VoidService;
import cn.toutatis.xvoid.spring.support.toolkits.MultiTenantManager;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Toutatis_Gc
 * @param <O> 实体类
 * @param <SERVICE>  继承了MP的service
 */
@Transactional(rollbackFor = Exception.class)
public class BaseControllerImpl<O extends EntityBasicAttribute<O>, SERVICE extends VoidService<O>> implements BaseController<O> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected SERVICE service;

    @Autowired
    protected VoidGlobalConfiguration voidGlobalConfiguration;

    protected Boolean platformMode = false;

    /**
     * 返回结果,可以在继承此基础的类直接操作返回结果
     */
    protected ProxyResult result = null;

    @Autowired
    protected MultiTenantManager multiTenantManager;

    @PostConstruct
    public void init() {
        platformMode = voidGlobalConfiguration.getPlatformMode();
        result = new ProxyResult(
                voidGlobalConfiguration.getGlobalServiceConfig().getUseDetailedMode(),
                voidGlobalConfiguration.getGlobalServiceConfig().getAutoConfig()
        );
//        result.setRequestId((String) request.getAttribute(StandardFields.FILTER_REQUEST_ID_KEY));
    }

    @Override
    @RequestMapping(value = "/getList",method = RequestMethod.POST)
    public Result getList() {
        QueryWrapper<O> queryWrapper = Wrappers.query();
        multiTenantManager.setBelongTo(queryWrapper);
        result.setData(Actions.SELECT,service.list(queryWrapper));
        return result;
    }

    @Override
    @Operation(summary = "获取实体列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pagingQuery",value="分页对象",required=false,paramType="query",dataTypeClass = PagingQuery.class),
            @ApiImplicitParam(name="obj",value="操作对象",required=false,paramType="query",dataTypeClass = Object.class)
    })
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public Result page(@RequestParam(required = false) PagingQuery pagingQuery,
                          @RequestParam(required = false) O obj
    ) {
        if (pagingQuery == null){ pagingQuery = new PagingQuery(); }
        String mchId = request.getHeader(StandardFields.VOID_REQUEST_HEADER_MCH_ID);
        Page<O> page;
        if (platformMode && Validator.strIsBlank(mchId)){
            return new ProxyResult(ResultCode.NOT_TENANT);
        }else if(!platformMode){
            page = service.pageList(pagingQuery,obj);
        }else{
            page = service.pageList(pagingQuery,obj);
        }
        Map<String,Object> objectMap = new HashMap<>(2);
        objectMap.put("list",page.getRecords());
        objectMap.put("totalCount",page.getTotal());
        result.setData(Actions.SELECT,objectMap);
        return result;
    }

    @Override
    @RequestMapping(value = "/getById",method = RequestMethod.POST)
    public Result getById(O entity) {
        if (entity == null){ return new ProxyResult(ResultCode.NOT_HAVE_SELECT_DATA); }
        QueryWrapper<O> oQueryWrapper = new QueryWrapper<>(entity);
        if (platformMode){
            String mchId = request.getHeader(StandardFields.VOID_REQUEST_HEADER_MCH_ID);
            if (Validator.strNotBlank(mchId)){
                oQueryWrapper.eq("mchId",mchId);
            }else {
                return new ProxyResult(ResultCode.NOT_TENANT);
            }
        }
        O one = service.getOneObj((Condition<O>) oQueryWrapper);
        result.setData(Actions.SELECT,one);
        return result;
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
