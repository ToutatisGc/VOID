package cn.toutatis.xvoid.spring.core.route.background.auth;

import cn.toutatis.redis.RedisCommonKeys;
import cn.toutatis.xvoid.common.result.ProxyResult;
import cn.toutatis.xvoid.common.result.Result;
import cn.toutatis.xvoid.common.result.ResultCode;
import cn.toutatis.xvoid.common.standard.AuthFields;
import cn.toutatis.xvoid.orm.base.authentication.entity.AccountRegistryEntity;
import cn.toutatis.xvoid.orm.base.authentication.entity.SystemUserLogin;
import cn.toutatis.xvoid.orm.base.authentication.enums.RegistryType;
import cn.toutatis.xvoid.orm.base.authentication.service.SystemUserLoginService;
import cn.toutatis.xvoid.spring.core.security.access.AuthValidationMessage;
import cn.toutatis.xvoid.spring.core.tools.ViewToolkit;
import cn.toutatis.xvoid.spring.annotations.application.VoidController;
import cn.toutatis.xvoid.spring.support.toolkits.VoidSpringToolkit;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;

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

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private SystemUserLoginService systemUserLoginService;

    @Autowired
    private VoidSpringToolkit voidSpringToolkit;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Operation(summary="后台管理系统登陆页面",description="管理后台登录页面访问地址")
    @RequestMapping(value = "/login/background/page",method = RequestMethod.GET)
    public ModelAndView backgroundLoginPage(){
        ModelAndView modelAndView = new ModelAndView(viewToolkit.toView("BackgroundLoginPage"));
        modelAndView.addObject("title","登录");
        return modelAndView;
    }

    @Operation(summary="管理员登录页面",description="管理员登录页面")
    @RequestMapping(value = "/login/xvoid/page",method = RequestMethod.GET)
    public ModelAndView administratorLoginPage(){
        ModelAndView modelAndView = new ModelAndView(viewToolkit.toView("AdministratorLoginPage"));
        modelAndView.addObject("title","管理员登录");
        return modelAndView;
    }

    /**
     * 在redis中存入验证键验证用户名
     * @param account 账户名
     * @param request 请求
     * @return 验证成功
     */
    @Operation(summary = "用户名预检",description = "预先检查用户表中是否有该用户")
    @RequestMapping(value = "/login/pre-check",method = RequestMethod.GET)
    public Result preCheck(
            @Parameter(description = "用户名",required = true) @RequestParam String account,
            @Parameter(description = "请求实体") HttpServletRequest request
    ){
        // 检查用户名合法
        if (!Validator.checkCNUsername(account)) {
            return new ProxyResult(ResultCode.AUTHENTICATION_PRE_CHECK_FAILED,"用户名不合法");
        }
        // 检查用户在数据库存在
        Boolean accountExist = systemUserLoginService.preCheckAccountExist(account);
        if (accountExist){
            // 设置当前session下所预检的账户并设置10分钟有效期
            BoundValueOperations<String, Object> sessionOps = redisTemplate.boundValueOps(
                    RedisCommonKeys.concat(AuthFields.LOGIN_PRE_CHECK_KEY,request.getSession().getId(), account)
            );
            sessionOps.set(account, Duration.ofMinutes(10L));
            return new ProxyResult(ResultCode.AUTHENTICATION_PRE_CHECK_SUCCESSFUL);
        }else {
            return new ProxyResult(ResultCode.AUTHENTICATION_PRE_CHECK_FAILED);
        }
    }

    @Operation(summary="用户注册",description="新用户注册")
    @RequestMapping(value = "/user/registry",method = RequestMethod.POST)
    public ProxyResult registry(@ApiParam("用户名") String account,
                                @ApiParam("信息载体") AccountRegistryEntity registryEntity,
                                @ApiParam("注册类型") RegistryType registryType){
        ProxyResult proxyResult = new ProxyResult(ResultCode.NORMAL_FAILED);
        proxyResult.setUseDetailedMode(true);
        // 确认填写用户名
        if(Validator.stringsNotNull(account)){
            // 确认填写密码
            if (!registryEntity.secretFilled()){
                proxyResult.setSupportMessage(AuthValidationMessage.SECRET_NOT_FILLED);
                return proxyResult;
            }
            // 确认密码匹配
            if (registryEntity.secretIsMatch()){
                // 进入不同认证类型
                Boolean accountExist = systemUserLoginService.preCheckAccountExist(account);
                if (accountExist){
                    proxyResult.setSupportMessage(AuthValidationMessage.USER_ALREADY_EXIST);
                    return proxyResult;
                }else {
                    SystemUserLogin newUser = new SystemUserLogin();
                    switch (registryType) {
                        case ACCOUNT -> {
                            newUser.setAccount(account);
                            newUser.setSecret(passwordEncoder.encode(registryEntity.getSecret()));
                        }
                        case EMAIL -> {
                            /*发送邮件*/
                        }
                        case PHONE -> {
                            /*发送短信验证码*/
                        }
                    }
                    systemUserLoginService.save(newUser);
                }
            }else {
                proxyResult.setSupportMessage(AuthValidationMessage.SECRET_NOT_MATCH);
            }
        }else {
            proxyResult.setSupportMessage(AuthValidationMessage.USERNAME_BLANK);
        }
        return proxyResult;
    }

    @ApiOperation(value="忘记密码",notes="忘记密码,重置用户密码")
    @RequestMapping(value = "/login/page",method = RequestMethod.POST)
    public ProxyResult forgetPassword(String username){
        return new ProxyResult(ResultCode.NORMAL_SUCCESS);
    }
}
