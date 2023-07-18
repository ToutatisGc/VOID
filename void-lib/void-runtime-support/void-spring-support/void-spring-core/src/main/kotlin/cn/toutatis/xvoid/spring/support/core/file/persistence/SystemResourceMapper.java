package cn.toutatis.xvoid.spring.support.core.file.persistence;

import cn.toutatis.xvoid.data.common.base.SystemResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统用户类 Mapper 接口
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2023-06-03
*/
@Mapper
@Repository
public interface SystemResourceMapper extends BaseMapper<SystemResource> {


}
