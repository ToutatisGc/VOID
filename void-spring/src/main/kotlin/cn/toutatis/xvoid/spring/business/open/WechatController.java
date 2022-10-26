package cn.toutatis.xvoid.spring.business.open;

import cn.toutatis.xvoid.spring.business.open.services.WechatOfficialAccountService;
import cn.toutatis.xvoid.support.spring.annotations.VoidController;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Toutatis_Gc
 * @date 2022/10/25 12:29
 */
@Api(tags = "微信接口", description = "微信公众号接口")
@ApiSupport(author = "Toutatis_Gc")
@RequestMapping("/wechat")
@VoidController
public class WechatController {

    private final Logger logger = LoggerToolkit.getLogger(this.getClass());

   private final WechatOfficialAccountService wechatOfficialAccountService;

    public WechatController(WechatOfficialAccountService wechatOfficialAccountService) {
        this.wechatOfficialAccountService = wechatOfficialAccountService;
    }

    @ApiOperation(
            value="微信公众号登录",
            notes="微信公众号基本配置下配置服务器URL,该接口用于接收微信推送消息")
    @RequestMapping(value = "/external/proxyLogin",method = {RequestMethod.GET})
    public void proxyLogin(HttpServletRequest request, HttpServletResponse response){

    }

    @ApiOperation(
            value="微信公众号推送接口",
            notes="微信公众号基本配置下配置服务器URL,该接口用于接收微信推送消息")
    @RequestMapping(value = "/external/receive",method = {RequestMethod.GET,RequestMethod.POST})
    public Object receive(HttpServletRequest request, HttpServletResponse response){
        return wechatOfficialAccountService.receive(request,response);
    }


}
