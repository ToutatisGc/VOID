package cn.toutatis.xvoid.orm.base.authentication.entity.derive;

import cn.toutatis.xvoid.common.enums.AuthType;
import com.alibaba.fastjson.JSONObject;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * 基本覆盖了用户所需的字段,如需其他认证,可自行集成
 * @author Toutatis_Gc
 */
@Setter
public class AbstractAuthInfo implements AuthInfo{

    /**
     * 用户名/邮箱/手机号等认证名
     */
    private String account;

    /**
     * 密钥
     * 例如:密码,加密,场景值等
     */
    private String secret;

    /**
     * 登录认证类型
     * 账号登录/第三方等
     */
    private AuthType authType;

    /**
     * 认证权限
     */
    private List<String> permissions;

    /**
     * 用户元数据,可自行添加额外字段
     */
    private JSONObject userMetaInfo;

    /**
     * SpringSecurity所需认证
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * 用户未过期
     */
    private boolean accountNonExpired;

    /**
     * 用户未锁定
     */
    private boolean accountNonLocked;

    /**
     * 用户是否启用
     */
    private boolean enabled = true;

    public AbstractAuthInfo() { }

    public AbstractAuthInfo(JSONObject userMetaInfo) {
        this.userMetaInfo = userMetaInfo;
    }

    @Override
    public List<String> getPermissions() {
        return this.permissions;
    }

    @Override
    public JSONObject getUserMetaInfo() {
        return userMetaInfo;
    }

    @Override
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.secret;
    }

    @Override
    public String getUsername() {
        return this.account;
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

}
