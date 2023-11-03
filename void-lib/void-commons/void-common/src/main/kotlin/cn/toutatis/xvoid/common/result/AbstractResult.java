package cn.toutatis.xvoid.common.result;

import cn.toutatis.xvoid.toolkit.formatting.JsonToolkit;
import cn.toutatis.xvoid.toolkit.formatting.StringNullSerializer;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Toutatis_Gc
 * 最终返回对象的中间抽象类
 * 对于部分方法做公共处理
 */
public abstract class AbstractResult implements Result {

    /**
     * [具体分发到派生类]
     * 响应数据
     */
    @Schema(name="请求响应数据",requiredMode = Schema.RequiredMode.REQUIRED,example = "{'name':'Toutatis_Gc'}")
    protected Object data;

    /**
     * [具体分发到派生类]
     * 当ResultCode为OP_REDIRECT时将此字段赋予派生类进行二次操作
     */
    @Schema(name="业务在页面中跳转地址",requiredMode = Schema.RequiredMode.NOT_REQUIRED,example = "/verify/email")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    protected String redirectUrl;

    public Object getData() {
        return data;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    @Override
    public Map<String, Object> getDataMap() {
        if (data == null){
            return null;
        }else{
            if (data instanceof Map){
                return (Map<String, Object>) data;
            }else if (data instanceof List<?>){
                List<?> data = (List<?>) this.data;
                LinkedHashMap<String, Object> flatList = new LinkedHashMap<>(data.size());
                for (int i = 0; i < data.size(); i++) {
                    flatList.put(i+"", data.get(i));
                }
                return flatList;
            }else {
                return JsonToolkit.parseJsonObject(data);
            }
        }
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
