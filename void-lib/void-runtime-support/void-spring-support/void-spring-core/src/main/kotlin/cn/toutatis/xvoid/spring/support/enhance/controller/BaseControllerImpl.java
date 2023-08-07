package cn.toutatis.xvoid.spring.support.enhance.controller;

import cn.toutatis.xvoid.common.standard.StandardFields;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.common.result.Actions;
import cn.toutatis.xvoid.common.result.ProxyResult;
import cn.toutatis.xvoid.common.result.Result;
import cn.toutatis.xvoid.common.result.ResultCode;
import cn.toutatis.xvoid.orm.support.Condition;
import cn.toutatis.xvoid.spring.configure.system.VoidConfiguration;
import cn.toutatis.xvoid.orm.support.mybatisplus.CommonWrapper;
import cn.toutatis.xvoid.orm.support.mybatisplus.PagingQuery;
import cn.toutatis.xvoid.orm.support.VoidService;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
    private HttpServletRequest request;

    @Autowired
    private SERVICE service;

    @Autowired
    private final VoidConfiguration voidConfiguration;

    private final Boolean platformMode;

    protected final CommonWrapper<O> wrapper = new CommonWrapper<>();

    private ProxyResult result;

    public BaseControllerImpl(VoidConfiguration voidConfiguration) {
        this.voidConfiguration = voidConfiguration;
        platformMode = voidConfiguration.getPlatformMode();
        result = new ProxyResult(
                voidConfiguration.getGlobalServiceConfig().getUseDetailedMode(),
                voidConfiguration.getGlobalServiceConfig().getAutoConfig()
        );
        result.setRequestId((String) request.getAttribute(StandardFields.FILTER_REQUEST_ID_KEY));
    }

    @Override
    @Operation(summary = "获取实体列表")
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
        if (platformMode && Validator.strIsBlank(mchId)){
            return new ProxyResult(ResultCode.NOT_TENANT);
        }else if(!platformMode){
            page = service.pageList(pagingQuery,obj);
        }else{
            page = service.pageList(pagingQuery,obj,mchId);
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
