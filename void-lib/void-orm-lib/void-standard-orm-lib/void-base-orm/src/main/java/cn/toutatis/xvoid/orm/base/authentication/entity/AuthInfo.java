package cn.toutatis.xvoid.orm.base.authentication.entity;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author Toutatis_Gc
 * 获取登录信息
 */
public interface AuthInfo extends UserDetails {

    List<String> getPermissions();

    JSONObject getUserInfo();

    void setPermissions(List<String> permissions);

}
