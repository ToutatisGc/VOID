package cn.toutatis.xvoid.creator.mapper;

import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * @author Toutatis_Gc
 */
public interface BasicMapper extends Mapper {
    /**
     * @param tableName 表名
     * @return 表信息
     * 获取具体一张表的信息
     */
    @MapKey("TABLE_NAME")
    List<Map<String,Object>> getTableInfo(Map<String,Object> map);

}
