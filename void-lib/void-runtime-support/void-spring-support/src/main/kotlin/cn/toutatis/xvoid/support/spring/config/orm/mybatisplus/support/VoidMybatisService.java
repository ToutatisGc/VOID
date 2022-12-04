package cn.toutatis.xvoid.support.spring.config.orm.mybatisplus.support;

import cn.toutatis.xvoid.support.SheetExportType;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Toutatis
 * 继承抽象服务
 * @param <T> 实体类
 */
public interface VoidMybatisService<T> extends IService<T> {

    /**
     * @param pagingQuery 分页对象
     * @param t 实体类
     * @return 分页结果
     */
    Page<T> getList(PagingQuery pagingQuery,T t);

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

}
