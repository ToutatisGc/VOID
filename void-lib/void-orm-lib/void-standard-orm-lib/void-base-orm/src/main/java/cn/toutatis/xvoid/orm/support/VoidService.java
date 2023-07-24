package cn.toutatis.xvoid.orm.support;

import cn.toutatis.xvoid.common.enums.sheet.SheetExportType;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.orm.support.mybatisplus.PagingQuery;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Toutatis
 * 继承抽象服务
 * @param <T> 实体类
 */
public interface VoidService<T extends EntityBasicAttribute<T>>{

    /**
     * @param pagingQuery 分页对象
     * @param t 实体类
     * @return 分页结果
     */
    Page<T> getList(PagingQuery pagingQuery, T t);

    /**
     * @param pagingQuery 分页对象
     * @param t 实体类
     * @param mchId 多租户ID
     * @return 分页结果
     */
    Page<T> getList(PagingQuery pagingQuery,T t,String mchId);

    /**
     * 导出表格数据
     * @param response 响应对象
     * @param sheetExportType 导出类型
     * @param data 导出数据
     * @param fileName 导出文件名
     */
    void export(HttpServletResponse response, SheetExportType sheetExportType, List<T> data, String fileName);

    T getOne(QueryWrapper<T> queryWrapper);

}
