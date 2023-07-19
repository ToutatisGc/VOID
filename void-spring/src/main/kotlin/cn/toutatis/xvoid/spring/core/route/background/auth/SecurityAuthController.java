package cn.toutatis.xvoid.spring.core.route.background.auth;

import cn.toutatis.xvoid.orm.base.data.common.result.ProxyResult;
import cn.toutatis.xvoid.orm.base.data.common.result.ResultCode;
import cn.toutatis.xvoid.orm.base.data.common.security.RegistryType;
import cn.toutatis.xvoid.spring.core.tools.ViewToolkit;
import cn.toutatis.xvoid.spring.annotations.application.VoidController;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @ApiOperation(value="用户注册",notes="新用户注册")
    @RequestMapping(value = "/user/registry",method = RequestMethod.POST)
    public ProxyResult registry(@RequestParam @ApiParam("用户名") String username,
                                @ApiParam("信息载体") JSONObject extra,
                                @ApiParam("注册类型") RegistryType registryType){
        boolean checkedNull = Validator.stringsNotNull(username);
        if(checkedNull){
            switch (registryType) {
                case ACCOUNT -> {
                    /*TODO 检查账号*/
                }
                case EMAIL -> {
                    /*发送邮件*/
                }
                case PHONE -> {
                    /*发送短信验证码*/
                }
                default -> throw new IllegalStateException("Unexpected value: " + registryType);
            }
        }
        return new ProxyResult(ResultCode.NORMAL_SUCCESS);
    }

    @ApiOperation(value="忘记密码",notes="忘记密码,重置用户密码")
    @RequestMapping(value = "/login/page",method = RequestMethod.POST)
    public ProxyResult forgetPassword(String username){
        return new ProxyResult(ResultCode.NORMAL_SUCCESS);
    }
}
