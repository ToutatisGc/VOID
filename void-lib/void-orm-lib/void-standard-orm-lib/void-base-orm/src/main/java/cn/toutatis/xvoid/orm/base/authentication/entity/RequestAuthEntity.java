package cn.toutatis.xvoid.orm.base.authentication.entity;

import cn.toutatis.xvoid.common.standard.AuthFields;
import cn.toutatis.xvoid.orm.base.authentication.enums.AuthType;
import com.alibaba.fastjson.JSONObject;


public class RequestAuthEntity {
    private JSONObject rawInformation;
    private String account;
    private AuthType authType;
    private String sessionId;

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
