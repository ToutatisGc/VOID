package cn.toutatis.xvoid.spring.business.open;

import cn.toutatis.xvoid.spring.annotations.application.VoidController;
import cn.toutatis.xvoid.spring.business.open.services.WechatOfficialAccountService;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static cn.toutatis.xvoid.common.Meta.ADMINISTRATOR;

/**
 * @author Toutatis_Gc
 * @date 2022/10/25 12:29
 */
@Tag(name = "微信接口", description = "微信公众号接口")
@ApiSupport
@RequestMapping("/third-party/wechat")
@VoidController
public class WechatController {

    private final Logger logger = LoggerToolkit.getLogger(this.getClass());

   private final WechatOfficialAccountService wechatOfficialAccountService;

    public WechatController(WechatOfficialAccountService wechatOfficialAccountService) {
        this.wechatOfficialAccountService = wechatOfficialAccountService;
    }

    @Operation(
            summary="微信公众号登录",
            description="微信公众号基本配置下配置服务器URL,该接口用于接收微信推送消息")
    @ApiOperationSupport(author = ADMINISTRATOR)
    @RequestMapping(value = "/external/proxyLogin",method = {RequestMethod.GET})
    public void proxyLogin(HttpServletRequest request, HttpServletResponse response){

    }

    @Operation(
            summary="微信公众号推送接口",
            description="微信公众号基本配置下配置服务器URL,该接口用于接收微信推送消息")
    @ApiOperationSupport(author = ADMINISTRATOR)
    @RequestMapping(value = "/external/receive",method = {RequestMethod.GET,RequestMethod.POST})
    public Object receive(HttpServletRequest request, HttpServletResponse response){
        return wechatOfficialAccountService.receive(request,response);
    }


}
