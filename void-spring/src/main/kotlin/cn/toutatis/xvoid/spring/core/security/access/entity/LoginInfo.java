package cn.toutatis.xvoid.spring.core.security.access.entity;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author Toutatis_Gc
 * 获取登录信息
 */
public interface LoginInfo {

    List<String> getPermissions();

    JSONObject getUserInfo();
}
