package cn.toutatis.xvoid.creater.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gc
 */
public interface BasicMapper extends Mapper {
    /**
     * @param tableName 表名
     * @return 表信息
     * 获取具体一张表的信息
     */
    List<Map> getTableInfo(Map map);
}
