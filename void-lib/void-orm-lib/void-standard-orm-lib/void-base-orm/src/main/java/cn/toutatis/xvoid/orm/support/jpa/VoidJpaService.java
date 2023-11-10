package cn.toutatis.xvoid.orm.support.jpa;

import cn.toutatis.xvoid.common.result.DataStatus;
import cn.toutatis.xvoid.common.result.ProxyResult;
import cn.toutatis.xvoid.orm.base.data.common.EntityBasicAttribute;
import cn.toutatis.xvoid.orm.support.VoidService;
import cn.toutatis.xvoid.orm.support.mybatisplus.PagingQuery;
import cn.toutatis.xvoid.toolkit.data.DataExportConfig;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author Toutatis_Gc
 */
public interface VoidJpaService<T extends EntityBasicAttribute<T>> extends VoidService<T> {

}
