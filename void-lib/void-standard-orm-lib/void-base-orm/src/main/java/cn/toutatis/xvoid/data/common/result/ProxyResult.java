package cn.toutatis.xvoid.data.common.result;

import cn.toutatis.xvoid.common.exception.MissingParameterException;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * @author Toutatis_Gc
 * 代理返回类
 * ResponseResultDispatcherAdvice 请求返回前拦截并区别simpleMode然后分发继承类
 */
@ApiModel(
        value = "代理返回结果(最终返回结果为Result的派生类)",
        description = "Controller标准返回结果,经过 ResponseResultDispatcherAdvice 分发到派生类")
public class ProxyResult implements Result {

    private final Logger logger = LoggerToolkit.getLogger(this.getClass());

    /**
     * [具体分发到派生类]
     * 返回结果响应码
     */
    @ApiModelProperty(name="状态码",required=true,example = "NORMAL_SUCCESS")
    private ResultCode resultCode;

    /**
     * [具体分发到派生类]
     * 返回响应消息
     */
    @ApiModelProperty(name="响应消息",required=true,example = "请求成功")
    private String message;

    /**
     * [具体分发到派生类]
     * 响应数据
     */
    @ApiModelProperty(name="请求响应数据",required=false,example = "{'name':'Toutatis_Gc'}")
    private Object data;

    /**
     * [具体分发到派生类]
     * 当前请求产生id
     */
    @ApiModelProperty(name="请求序列",required=false,example = "1111-2222-3333-DDDD")
    private String requestId;

    /**
     * 是否使用详细响应模式
     * 详细模式会有更多属性,但是会增大数据大小,适合开发模式使用
     * 该属性默认在springboot下由VoidConfiguration和BaseControllerImpl类所产生
     */
    @JsonIgnore
    private Boolean useDetailedMode = null;

    /**
     * 是否自动配置响应状态和消息
     * 默认使用参数ResultCode的code和info字段
     * 此配置还会自动判断响应数据,并加以配置响应状态
     * 但是需要注意的是需要配置Actions动作来完成自动注入属性
     */
    @JsonIgnore
    private Boolean autoConfig = true;

    /**
     * 此次请求对应动作
     */
    @JsonIgnore
    private Actions action = null;

    /**
     * 是否冻结对象禁止二次setData方法
     */
    @JsonIgnore
    private Boolean freeze = false;

    /**
     * 是否为成功标志
     * 该字段用于autoConfig模式下
     * 作用是判断data填充是否为空或bool情况
     * */
    private Boolean successfulSign = false;

    /**
     * 是否已经编辑过,如果编辑过并且冻结对象,将不能再编辑对象内容
     */
    private Boolean alreadyEdited = false;

    public ProxyResult(ResultCode resultCode) {
        this.resultCode = resultCode;
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

    public ProxyResult(Boolean useDetailedMode, Boolean autoConfig) {
        this.useDetailedMode = useDetailedMode;
        this.autoConfig = autoConfig;
    }

    @Override
    public void setData(Object data) {
        if (this.alreadyEdited && this.freeze){
            System.out.println(this.hashCode()+"对象已经冻结,data将不可二次编辑");
            return;
        }
        if (!autoConfig && resultCode == null){
            logger.warn("返回结果返回码未标记会出现响应状态码异常[解决方案：添加resultCode]");
            throw new MissingParameterException();
        }
        if (autoConfig){
            setData(action, data);
        }else{
            this.data = data;
        }
    }

    /**
     * 设置返回对象动作和数据,加以自行判断
     * @param action 动作来源
     * @param data 数据
     */
    public void setData(Actions action,Object data){
        this.action = action;
        this.alreadyEdited = true;
        setContent(data);
    }

    private enum Classify{
        /**
         * 未知
         */
        UNKNOWN,
        /**
         * 列表
         */
        LIST,
        /**
         * map类型
         */
        MAP,
        /**
         * 对象实例
         */
        OBJECT,
        /**
         * 条件类型
         */
        BOOLEAN;
    }

    /**
     * 设置具体内容属性
     * @param data 数据
     */
    private void setContent(Object data){
        this.data = data;
        if (autoConfig){
            if (action != null){
                Classify classify = Classify.UNKNOWN;
                boolean notEmpty = false;
                if (data != null) {
                    Class<?> dataClass = data.getClass();
                    if (List.class.isAssignableFrom(dataClass)) {
                        List<?> lengthSize = (List<?>) data;
                        this.successfulSign = notEmpty = lengthSize.size() > 0;
                        classify = Classify.LIST;
                    } else if (dataClass == JSONObject.class) {
                        JSONObject tmpData = (JSONObject) this.data;
                        this.successfulSign = notEmpty = tmpData.size() > 0;
                        classify = Classify.MAP;
                    } else if (Map.class.isAssignableFrom(dataClass)) {
                        Map<?,?> mapSize = (Map<?,?>) data;
                        this.successfulSign = notEmpty = !mapSize.isEmpty();
                        classify = Classify.MAP;
                    } else if (String.class.isAssignableFrom(dataClass)) {
                        String tmpData = (String) this.data;
                        this.successfulSign = notEmpty = tmpData.length() > 0;
                        classify = Classify.OBJECT;
                    } else if (Object.class.isAssignableFrom(dataClass)) {
                        this.successfulSign = notEmpty = true;
                        classify = Classify.OBJECT;
                    } else {
                        this.successfulSign = notEmpty = false;
                        classify = Classify.UNKNOWN;
                    }
                }
                //        获取动作,如果是更新等操作,获取的为sign的标志,查询为自动设置的content内容是否为空
                if (Classify.UNKNOWN != classify){
                    switch (action){
                        case SELECT:
                            updateEnv(notEmpty,ResultCode.NORMAL_SUCCESS,ResultCode.NORMAL_SUCCESS_BUT_NULL);
                            break;
                        case UPDATE:
                            checkAutoSign(ResultCode.UPDATE_SUCCESS,ResultCode.UPDATE_FAILED);
                            break;
                        case CHECKED:
                            checkAutoSign(ResultCode.APPLY_SUCCESS,ResultCode.APPLY_FAILED);
                            break;
                        case INSERT:
                            checkAutoSign(ResultCode.INSERT_SUCCESS,ResultCode.INSERT_FAILED);
                            break;
                        case DELETE:
                            checkAutoSign(ResultCode.DELETE_SUCCESS,ResultCode.DELETE_FAILED);
                            break;
                        default:
                            break;
                    }
                }else {
                    updateEnv(ResultCode.NULL_DIRECT_CODE);
                }
            }else{
                // 没有任何动作标记,视为普通请求
                updateEnv(ResultCode.NORMAL_SUCCESS);
            }
        }
    }

    private void checkAutoSign(ResultCode successCode,ResultCode failCode){
        if (this.successfulSign){
            updateEnv(successCode);
        }else {
            updateEnv(failCode);
        }
    }

    /**
     * 更新响应属性
     * @param code 响应码
     */
    private void updateEnv(ResultCode code){
        this.setResultCode(code);
        this.setMessage(code.getInfo());
    }

    private void updateEnv(boolean b,ResultCode success,ResultCode failCode){
        if (b){
            updateEnv(success);
        }else {
            updateEnv(failCode);
        }
    }

    @Override
    public Object serialize() {
        return this;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    @Override
    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Boolean getUseDetailedMode() {
        return useDetailedMode;
    }

    public void setUseDetailedMode(Boolean useDetailedMode) {
        this.useDetailedMode = useDetailedMode;
    }

    public void setAutoConfig(Boolean autoConfig) {
        this.autoConfig = autoConfig;
    }

    public void setAction(Actions action) {
        this.action = action;
    }

    public void setFreeze(Boolean freeze) {
        this.freeze = freeze;
    }
}
