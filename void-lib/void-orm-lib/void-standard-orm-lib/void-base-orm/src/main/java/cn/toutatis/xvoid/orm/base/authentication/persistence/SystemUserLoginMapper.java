package cn.toutatis.xvoid.orm.base.authentication.persistence;

import cn.toutatis.xvoid.orm.base.authentication.entity.SystemUserLogin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 系统用户类 Mapper 接口
 * </p>
 *
 * @author Toutatis_Gc
 * @since 2022-07-13
*/
@Mapper
@Repository
public interface SystemUserLoginMapper extends BaseMapper<SystemUserLogin> {

    /**
     * 查询用户表中该用户名是否存在
     * 用于登录自检业务和用户注册业务
     * @param account 帐户名
     * @return 帐户名是否存在
     */
    @Select("SELECT 1 AS `check` FROM " + SystemUserLogin.TABLE + " WHERE account = #{account} OR email = #{account} OR phoneCode = #{account} LIMIT 1")
    Boolean selectAccountExist(@Param("account") String account);

}
