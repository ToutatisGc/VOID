package cn.toutatis.data.common;

import cn.toutatis.data.implement.Result;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProxyResult implements Result {

    private ResultCode resultCode;

    private String message;

    private Object data;

    @JsonIgnore
    private Boolean useSimpleMode = null;

    @JsonIgnore
    private boolean autoConfig = true;

    public ProxyResult(boolean useSimpleMode, boolean autoConfig) {
        this.useSimpleMode = useSimpleMode;
        this.autoConfig = autoConfig;
    }

    public ProxyResult(boolean useSimpleMode) {
        this.useSimpleMode = useSimpleMode;
    }

    public ProxyResult(ResultCode resultCode, String message, Object data) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = data;
    }

    public Boolean isUseSimpleMode() {
        return useSimpleMode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Object serialize() {
        return this;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }

    public void setData(Object data, boolean autoConfig){
        this.setData(data);
        this.autoConfig = autoConfig;
    }

    public Object getData() {
        return data;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    @Override
    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
