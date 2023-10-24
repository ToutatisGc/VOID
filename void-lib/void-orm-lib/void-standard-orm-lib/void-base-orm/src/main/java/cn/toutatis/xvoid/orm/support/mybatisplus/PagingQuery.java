package cn.toutatis.xvoid.orm.support.mybatisplus;

import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import lombok.Data;

/**
 * @author Toutatis_Gc
 * 分页查询对象
 */
@Data
public class PagingQuery {

    /**
     * 默认查询第一页
     */
    private Integer currentPage = 1;

    /**
     * 默认分页大小为15个记录
     */
    private Integer pageSize = 15;

    /**
     * 排序字段
     */
    private String defaultOrderColumn = EntityBasicAttribute.CREATE_TIME_COLUMN_NAME;

    /**
     * 是否正序排序
     */
    private Boolean asc = true;

}
