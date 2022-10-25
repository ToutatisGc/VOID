package cn.toutatis.xvoid.spring.business.open;

import cn.toutatis.xvoid.data.common.result.ProxyResult;
import cn.toutatis.xvoid.data.common.result.ResultCode;
import cn.toutatis.xvoid.spring.business.open.request.WechatOfficialAccountRequest;
import cn.toutatis.xvoid.support.spring.annotations.VoidController;
import cn.toutatis.xvoid.support.spring.toolkits.VoidSpringToolkit;
import cn.toutatis.xvoid.toolkit.formatting.XmlToolkit;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

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

    private final WechatOfficialAccountRequest wechatOfficialAccountRequest;

    private final VoidSpringToolkit voidSpringToolkit;

    public WechatController(
            VoidSpringToolkit voidSpringToolkit,
            WechatOfficialAccountRequest wechatOfficialAccountRequest) {
        this.voidSpringToolkit=voidSpringToolkit;
        this.wechatOfficialAccountRequest = wechatOfficialAccountRequest;
    }

    /**
     *
     * 接入文档 https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Access_Overview.html
     * @param request 请求
     * @param response 响应
     */
    @ApiOperation(
            value="微信公众号推送接口",
            notes="微信公众号基本配置下配置服务器URL,该接口用于接收微信推送消息")
    @RequestMapping(value = "/external/receive",method = {RequestMethod.GET,RequestMethod.POST})
    public Object receive(HttpServletRequest request, HttpServletResponse response){
        String nonce = request.getParameter("nonce");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        if (wechatOfficialAccountRequest.checkSource(timestamp,nonce,signature)){
            StringBuilder requestBodyBuilder = new StringBuilder();
            try (BufferedReader bufferedReader = request.getReader()) {
                bufferedReader.lines().forEach(requestBodyBuilder::append);
            }catch(IOException ignored){}
            String requestBody = requestBodyBuilder.toString();
            if (Validator.strNotBlank(requestBody) && "text/xml".equals(request.getContentType())){
                JSONObject xmlMap = XmlToolkit.xmlToMap(requestBody);
                logger.info(wechatOfficialAccountRequest.messageConvertLogFormat(xmlMap,voidSpringToolkit.getRid(request)));
            }
            String echoStr = request.getParameter("echostr");
            if (echoStr != null){
                return echoStr;
            }else{
                return new ProxyResult(ResultCode.UNKNOWN_EXCEPTION);
            }
        }else{
            return new ProxyResult(ResultCode.ILLEGAL_OPERATION);
        }
    }

}
