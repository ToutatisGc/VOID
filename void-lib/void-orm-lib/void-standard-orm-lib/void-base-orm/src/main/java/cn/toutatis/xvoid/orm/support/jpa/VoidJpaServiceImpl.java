package cn.toutatis.xvoid.orm.support.jpa;

import cn.toutatis.xvoid.common.enums.sheet.SheetExportType;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.orm.support.VoidService;
import cn.toutatis.xvoid.orm.support.mybatisplus.PagingQuery;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Spring Data JPA 标准仓库
 * @param <R> 继承了JpaRepo的仓库类
 * @param <ID> ID类型
 * @param <T> 实体类
 * @author Toutatis_Gc
 */
public class VoidJpaServiceImpl<R extends VoidJpaRepo<T,ID>,ID,T extends EntityBasicAttribute<T>> implements VoidService<T> {

    @Autowired
    protected R repository;



}
