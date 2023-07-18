package ${package.Controller};


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
<#--import cn.toutatis.core.root.systemService.service.SystemDictionaryService;-->
<#--import org.springframework.context.annotation.Scope;-->
<#--import org.springframework.transaction.annotation.Transactional;-->
<#if restControllerStyle>
import cn.toutatis.xvoid.spring.support.enhance.controller.BaseControllerImpl;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import cn.toutatis.xvoid.spring.support.spring.annotations.VoidController;
import cn.toutatis.xvoid.spring.support.spring.annotations.LogHandle;
import cn.toutatis.xvoid.spring.support.config.VoidConfiguration;
<#--import org.springframework.web.bind.annotation.RestController;-->
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if swagger2>
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${entity} 前端控制器
 * ${table.comment!}
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
<#--@RestController-->
@Polymerization
@LogHandle("${table.controllerName} ${table.comment!}")
@Api(tags = "${entity}前端控制器",description = "${table.controllerName}'s APIs")
@ApiSupport(author = "${author}")
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} extends BaseControllerImpl<${entity}, ${table.serviceName}> {
</#if>

    /**
    * 构造器注入配置
    * @param voidConfiguration 环境配置
    */
    public ${table.controllerName}(VoidConfiguration voidConfiguration) { super(voidConfiguration); }
<#--    @Autowired-->
<#--    SystemDictionaryService dictionaryService;-->

    @ApiOperation(value="${entity}导航页面",notes="${table.comment!}导航页面")
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("page/${entity}Index");
<#--        modelAndView.addObject("status",dictionaryService.staticDic("SYSTEM_STATUS_0000"));-->
        return modelAndView;
    }

    /*   自定义后台方法区域   */


    /*   对外开放接口方法区域   */


}
</#if>
