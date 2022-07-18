package cn.toutatis.xvoid.toolkit.data;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Toutatis_Gc
 * POI解析标准模型
 */
public class PoiStandardModel {

    /**
     * 表头字段映射
     */
    private final LinkedHashMap<String,String> headers;

    /**
     * 数据列表
     */
    private final List<Object> data;

    public PoiStandardModel(LinkedHashMap<String, String> headers, List<Object> data) {
        this.headers = headers;
        this.data = data;
    }

    public void export(Workbook sheets,String title) {
        List<List<Object>> columns = new ArrayList<>(data.size());
        for (Object datum : data) {
            List<Object> column = new ArrayList<>(headers.size());
            if (datum instanceof JSONObject){
                JSONObject jsonObj = (JSONObject) datum;
                headers.forEach((k,v)->{
                    String o = jsonObj.getString(k);
                    if (o != null){
                        column.add(o);
                    }else{
                        column.add("");
                    }
                });
            }else if (datum instanceof Map){
                Map<String, Object> map = (Map<String, Object>) datum;
                headers.forEach((k,v)->{
                    Object o = map.get(k);
                    if (o != null){
                        column.add(o);
                    }else{
                        column.add("");
                    }
                });
            }
            columns.add(column);
        }
        List<String> headerList = new ArrayList<>(headers.size());
        headers.forEach((k,v)-> headerList.add(v));
    }

}
