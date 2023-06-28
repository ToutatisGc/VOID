package cn.toutatis.xvoid.toolkit.data;


import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * POI解析标准模型
 * @author Toutatis_Gc
 */
public class PoiStandardModel {

    /**
     * 表头字段映射
     */
    private final LinkedHashMap<String,String> headers;

    /**
     * 数据列表
     */
    private final List<?> data;

    public PoiStandardModel(LinkedHashMap<String, String> headers, List<?> data) {
        this.headers = headers;
        this.data = data;
    }

    public LinkedHashMap<String, String> getHeaders() {
        return headers;
    }

    public List<?> getData() {
        return data;
    }

}
