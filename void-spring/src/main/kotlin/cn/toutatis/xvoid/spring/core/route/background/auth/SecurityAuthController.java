package cn.toutatis.xvoid.spring.core.route.background.auth;

import cn.toutatis.xvoid.spring.core.tools.ViewToolkit;
import cn.toutatis.xvoid.support.spring.annotations.VoidController;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Toutatis_Gc
 * @date 2022/11/21 16:24
 */
@Api(tags = "权限API", description = "权限部分接口")
@ApiSupport(order = 0, author = "Toutatis_Gc")
@VoidController
@RequestMapping("/auth")
public class SecurityAuthController {

    private final ViewToolkit viewToolkit = new ViewToolkit("pages/background/auth");

    @ApiOperation(value="系统登陆页",notes="管理后台登录页面访问地址")
    @RequestMapping(value = "/login/page",method = RequestMethod.GET)
    public ModelAndView backgroundLoginPage(){
        ModelAndView modelAndView = new ModelAndView(viewToolkit.toView("BackgroundLoginPage"));
        modelAndView.addObject("title","登录");
        return modelAndView;
    }

}
