package cn.toutatis.xvoid.spring.business.user.entity;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author Toutatis_Gc
 * @date 2022/11/23 11:48
 * 标准版表单登录用户
 * 该类用户通常使用页面登录可见页面
 */
public class FormUserDetails implements UserDetails, AuthInfo {

    private Collection<? extends GrantedAuthority> authorities;

    private JSONObject userInfo;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean enabled = true;

    private List<String> permissions;

    private String secret;

    public FormUserDetails() {
    }

    public FormUserDetails(JSONObject userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public List<String> getPermissions() {
        return permissions;
    }

    @Override
    public JSONObject getUserInfo() {
        return userInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String getPassword() {
        return secret;
    }

    @Override
    public String getUsername() {
        return userInfo.getString("username");
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setUserInfo(JSONObject userInfo) {
        this.userInfo = userInfo;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
