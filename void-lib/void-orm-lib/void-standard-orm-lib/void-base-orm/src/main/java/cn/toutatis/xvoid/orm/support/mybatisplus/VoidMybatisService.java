package cn.toutatis.xvoid.orm.support.mybatisplus;

import cn.toutatis.xvoid.common.enums.sheet.SheetExportType;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.orm.support.VoidService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Toutatis_Gc
 */
public interface VoidMybatisService<T extends EntityBasicAttribute<T>> extends VoidService<T>,IService<T> {

    @Override
    Page<T> getList(PagingQuery pagingQuery, T t);

    @Override
    Page<T> getList(PagingQuery pagingQuery, T t, String mchId);

    @Override
    void export(HttpServletResponse response, SheetExportType sheetExportType, List<T> data, String fileName);

    T getOne(QueryWrapper<T> queryWrapper);

}