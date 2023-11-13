package cn.toutatis.xvoid.orm.base.authentication.entity;

import cn.toutatis.xvoid.common.standard.AuthFields;
import cn.toutatis.xvoid.common.enums.AuthType;
import com.alibaba.fastjson.JSONObject;


/**
 * 请求登录JSON转换实体
 * @author Toutatis_Gc
 */
public class RequestAuthEntity {

    /**
     * 登录载荷
     */
    private final JSONObject rawInformation;

    /**
     * 登录账户
     */
    private final String account;

    /**
     * 登录类型
     */
    private final AuthType authType;

    /**
     * 请求sessionId
     */
    private final String sessionId;

    public RequestAuthEntity(JSONObject rawInformation) {
        this.rawInformation = rawInformation;
        this.account = rawInformation.getString(AuthFields.ACCOUNT);
        this.authType = rawInformation.getString(AuthFields.AUTH_TYPE) != null ? AuthType.valueOf(rawInformation.getString(AuthFields.AUTH_TYPE)) : null;
        this.sessionId = rawInformation.getString(AuthFields.XVOID_INTERNAL_ACTIVITY_AUTH_SESSION_KEY);
    }

    public JSONObject getRawInformation() {
        return rawInformation;
    }

    public String getAccount() {
        return account;
    }

    public AuthType getAuthType() {
        return authType;
    }

    public String getSessionId() {
        return sessionId;
    }
}
