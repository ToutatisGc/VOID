package cn.toutatis.xvoid.spring.business.user.persistence;

import cn.toutatis.xvoid.data.common.security.SystemAuthPath;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 系统权限类 Mapper 接口
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-11-26
*/
@Mapper
@Repository
public interface SystemAuthPathMapper extends BaseMapper<SystemAuthPath> {

    /**
     * 直接使用sql获取权限,该方法简单但是不利于流程
     * @param userId 用户id
     * @return 权限列表
     */
    @Select("")
    public List<String> selectPermissionsDirect(@Param("userId") String userId);

}
