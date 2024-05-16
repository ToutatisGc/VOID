package cn.toutatis.xvoid.spring.business.open.services;

import cn.toutatis.xvoid.common.result.ProxyResult;
import cn.toutatis.xvoid.common.result.ResultCode;
import cn.toutatis.xvoid.spring.business.open.request.WechatOfficialAccountRequest;
import cn.toutatis.xvoid.spring.support.toolkits.VoidSpringToolkit;
import cn.toutatis.xvoid.toolkit.formatting.XmlToolkit;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Toutatis_Gc
 * @date 2022/10/25 20:01
 */
@Service
public class WechatOfficialAccountService {


    private final Logger logger = LoggerToolkit.getLogger(this.getClass());

    private final WechatOfficialAccountRequest wechatOfficialAccountRequest;

    private final VoidSpringToolkit voidSpringToolkit;

    public WechatOfficialAccountService(
            VoidSpringToolkit voidSpringToolkit,
            WechatOfficialAccountRequest wechatOfficialAccountRequest) {
        this.voidSpringToolkit=voidSpringToolkit;
        this.wechatOfficialAccountRequest = wechatOfficialAccountRequest;
    }

    public void webAuthorization(HttpServletRequest request, HttpServletResponse response){

    }


    /**
     *
     * 接入文档 https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Access_Overview.html
     * @param request 请求
     * @param response 响应
     */
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
