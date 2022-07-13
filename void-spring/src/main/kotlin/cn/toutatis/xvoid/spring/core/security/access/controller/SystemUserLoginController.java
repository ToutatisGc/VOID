package cn.toutatis.xvoid.spring.core.security.access.controller;


import cn.toutatis.data.common.result.ProxyResult;
import cn.toutatis.data.common.security.SystemUserLogin;
import cn.toutatis.xvoid.support.spring.config.VoidConfiguration;
import cn.toutatis.xvoid.support.spring.config.orm.mybatisplus.support.CommonWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import cn.toutatis.xvoid.support.spring.enhance.controller.BaseControllerImpl;
import cn.toutatis.xvoid.spring.core.security.access.service.SystemUserLoginService;
import cn.toutatis.xvoid.support.spring.annotations.Polymerization;
import cn.toutatis.xvoid.support.spring.annotations.LogHandle;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * SystemUserLogin 前端控制器
 * 系统用户类
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-07-13
 */
@Polymerization
@LogHandle("SystemUserLoginController 系统用户类")
@Api(tags = "SystemUserLogin前端控制器",description = "SystemUserLoginController's APIs")
@ApiSupport(author = "Toutatis_Gc")
@RequestMapping("/security/systemUserLogin")
public class SystemUserLoginController extends BaseControllerImpl<SystemUserLogin, SystemUserLoginService> {

    /**
     * 构造器注入配置
     * @param voidConfiguration
     */
    public SystemUserLoginController(VoidConfiguration voidConfiguration) { super(voidConfiguration); }

    @ApiOperation(value="SystemUserLogin导航页面",notes="系统用户类导航页面")
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("page/SystemUserLoginIndex");
        return modelAndView;
    }

    /*   自定义后台方法区域   */


    /*   对外开放接口方法区域   */


}
