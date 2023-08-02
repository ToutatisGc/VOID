package cn.toutatis.xvoid.spring.core.route.background.auth;

import cn.toutatis.xvoid.common.result.ProxyResult;
import cn.toutatis.xvoid.common.result.ResultCode;
import cn.toutatis.xvoid.orm.base.authentication.enums.RegistryType;
import cn.toutatis.xvoid.spring.core.tools.ViewToolkit;
import cn.toutatis.xvoid.spring.annotations.application.VoidController;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Toutatis_Gc
 * @date 2022/11/21 16:24
 */
@Tag(name = "权限API", description = "权限部分接口")
@ApiSupport(order = 0, author = "Toutatis_Gc")
@VoidController
@RequestMapping("/auth")
public class SecurityAuthController {

    private final ViewToolkit viewToolkit = new ViewToolkit("pages/background/auth");

    @Operation(summary="系统登陆页",description="管理后台登录页面访问地址")
    @RequestMapping(value = "/login/page",method = RequestMethod.GET)
    public ModelAndView backgroundLoginPage(){
        ModelAndView modelAndView = new ModelAndView(viewToolkit.toView("BackgroundLoginPage"));
        modelAndView.addObject("title","登录");
        return modelAndView;
    }

    @Operation(summary="用户注册",description="新用户注册")
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
