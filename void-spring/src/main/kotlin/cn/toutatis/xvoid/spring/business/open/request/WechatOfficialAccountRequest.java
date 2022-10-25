package cn.toutatis.xvoid.spring.business.open.request;

import cn.toutatis.xvoid.spring.business.open.constant.WechatMessageType;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author Toutatis_Gc
 * @date 2022/10/25 13:36
 * 微信公众号开发请求
 */
@Data
public class WechatOfficialAccountRequest {

    private final Logger logger = LoggerToolkit.getLogger(WechatOfficialAccountRequest.class);

    private WechatOfficialAccountRequest(){}

    /**
     * 必必要参数
     * @param appId 微信公众号AppId
     * @param appSecret 微信公众号AppSecret
     */
    public WechatOfficialAccountRequest(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    private String appId;

    private String appSecret;

    private String token;

    /**
     * 校验消息是否来自微信
     * @param timestamp 时间戳(由公众号传入)
     * @param nonce 场景值(由公众号传入)
     * @param signature 标志(由公众号传入)
     * @return 是否来自官方
     */
    public Boolean checkSource(String timestamp ,String nonce,String signature){
        if (Validator.stringsNotNull(timestamp, nonce, signature)){
            if (Validator.strIsBlank(token)){
                logger.error("[VOID-THIRD]缺失公众号配置token字段");
                return false;
            }
            String[] paramArr = new String[] {token, timestamp, nonce};
            Arrays.sort(paramArr);
            String content  = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);
            return DigestUtils.sha1Hex(content.getBytes(StandardCharsets.UTF_8)).equals(signature);
        }else{
            logger.error("[VOID-THIRD]缺失字段,TIMESTAMP:{},NONCE:{},SIGNATURE:{}",timestamp,nonce,signature);
            return false;
        }
    }

    /**
     * @param message 微信发送消息
     * @param rid 请求ID
     * @return
     */
    public String messageConvertLogFormat(JSONObject message,String rid){
        StringBuilder sb = new StringBuilder("[WECHAT-MESSAGE]");
        if (rid != null){
            sb.append("[RID:").append(rid).append("]");
        }
        String msgType = message.getString("MsgType");
        message.remove("MsgType");
        WechatMessageType wechatMessageType = WechatMessageType.valueOf(msgType.toUpperCase());
        switch (wechatMessageType){
            case TEXT:
            case EVENT:
                sb.append("[消息类型:").append(wechatMessageType.getDescription()).append("]");
                String event = (String) message.getOrDefault("Event", "未知");
                sb.append("[事件类型:").append(event).append("]");
                break;
            default:
                sb.append("[消息类型: 未知]");
                break;
        }
        message.remove("Event");
        sb.append("[业务字段:");
        for (String key : message.keySet()) {
            sb.append(key).append("=").append(message.getString(key)).append(" ");
        }
        sb.append("]");
        return sb.toString();
    }

}
