package cn.toutatis.xvoid.orm.base.authentication.entity;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author Toutatis_Gc
 * 获取登录信息
 */
public interface AuthInfo extends UserDetails {

    /**
     * 用认证过程填充用户可使用的认证路径
     * @return 用户可使用的AntPath
     */
    List<String> getPermissions();

    /**
     * 用户认证时使用的数据,可自定义填充
     * @return 元数据
     */
    JSONObject getUserMetaInfo();

    /**
     * 设置用户可认证的路径
     * @param permissions 路径Path集合
     */
    void setPermissions(List<String> permissions);

}
