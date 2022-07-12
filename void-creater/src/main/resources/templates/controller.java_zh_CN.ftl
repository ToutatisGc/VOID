package ${package.Controller};


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
<#--import cn.toutatis.core.root.systemService.service.SystemDictionaryService;-->
<#--import org.springframework.context.annotation.Scope;-->
<#--import org.springframework.transaction.annotation.Transactional;-->
<#if restControllerStyle>
import cn.toutatis.common.controllerDepand.BaseControllerImpl;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import cn.toutatis.common.controllerDepand.Polymerization;
import cn.toutatis.common.log.LogBackHandle;
<#--import org.springframework.web.bind.annotation.RestController;-->
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
<#--@RestController-->
@Polymerization
@LogBackHandle("${table.controllerName} ${table.comment!}")
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

<#--    @Autowired-->
<#--    SystemDictionaryService dictionaryService;-->

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
