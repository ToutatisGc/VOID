package cn.toutatis.xvoid.orm.base.data.common.result;

import cn.toutatis.xvoid.toolkit.formatting.JsonToolkit;
import io.swagger.annotations.ApiModelProperty;
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

    public Object getData() {
        return data;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }

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
}
