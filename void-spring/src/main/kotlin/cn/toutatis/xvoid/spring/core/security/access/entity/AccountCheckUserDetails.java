package cn.toutatis.xvoid.spring.core.security.access.entity;

import cn.toutatis.data.common.security.SystemUserLogin;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class AccountCheckUserDetails extends SystemUserLogin implements UserDetails {

    private Collection<? extends GrantedAuthority> authorities;

    private List<JSONObject> remoteDoubleCheckPermissionList;

    public List<JSONObject> getRemoteDoubleCheckPermissionList() {
        return remoteDoubleCheckPermissionList;
    }

    public void setRemoteDoubleCheckPermissionList(List<JSONObject> remoteDoubleCheckPermissionList) {
        this.remoteDoubleCheckPermissionList = remoteDoubleCheckPermissionList;
    }

    @TableField(exist = false)
    private boolean accountNonExpired;

    @TableField(exist = false)
    private boolean accountNonLocked;

    @TableField(exist = false)
    private boolean credentialsNonExpired;

    @TableField(exist = false)
    private boolean enabled;

    @TableField(exist = false)
    private List<String> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return super.getSecret();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
