package cn.toutatis.xvoid.spring.business.user.entity;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author Toutatis_Gc
 * 获取登录信息
 */
public interface AuthInfo {

    List<String> getPermissions();

    JSONObject getUserInfo();
}
