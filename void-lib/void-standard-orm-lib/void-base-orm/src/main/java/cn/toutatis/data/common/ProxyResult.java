package cn.toutatis.data.common;

import cn.toutatis.data.implement.Result;
import cn.toutatis.toolkit.log.LoggerToolkit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;

/**
 * @author Toutatis_Gc
 * 代理返回类
 * ResponseResultDispatcherAdvice 请求返回前拦截并区别simpleMode然后分发继承类
 */
@ApiModel(
        value = "代理返回结果(最终返回结果为派生类)",
        description = "Controller标准返回结果,经过 ResponseResultDispatcherAdvice 分发到派生类")
public class ProxyResult implements Result {

    private final Logger logger = LoggerToolkit.INSTANCE.getLogger(this.getClass());

    @ApiModelProperty(name="状态码",required=true,example = "NORMAL_SUCCESS")
    private ResultCode resultCode;

    @ApiModelProperty(name="响应消息",required=true,example = "请求成功")
    private String message;

    @ApiModelProperty(name="请求响应数据",required=false,example = "{'name':'Toutatis_Gc'}")
    private Object data;

    @ApiModelProperty(name="请求序列",required=false,example = "1111-2222-3333-DDDD")
    private String requestId;

    @JsonIgnore
    private Boolean useDetailedMode = false;

    @JsonIgnore
    private boolean autoConfig = true;

    public ProxyResult(ResultCode resultCode) {
        this.resultCode = resultCode;
        this.message = resultCode.getInfo();
    }

    public ProxyResult(Object data) {
        this.setData(data);
    }

    public ProxyResult(ResultCode resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    public ProxyResult(ResultCode resultCode, String message, Object data) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = data;
    }

    public ProxyResult(boolean useSimpleMode, boolean autoConfig) {
        this.useDetailedMode = useSimpleMode;
        this.autoConfig = autoConfig;
    }

    public ProxyResult(boolean useSimpleMode) {
        this.useDetailedMode = useSimpleMode;
    }

    public Boolean getUseDetailedMode() {
        return useDetailedMode;
    }

    public void setUseDetailedMode(Boolean useDetailedMode) {
        this.useDetailedMode = useDetailedMode;
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
        if (!autoConfig && resultCode == null){
            logger.warn("返回结果返回码未标记会出现响应状态码异常[解决方案添加resultCode]");
        }
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
