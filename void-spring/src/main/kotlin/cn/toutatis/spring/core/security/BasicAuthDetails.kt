package cn.toutatis.spring.core.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.GrantedAuthority
import com.baomidou.mybatisplus.annotation.TableField

/**
 * @author Toutatis_Gc
 * SpringBoot Security基础用户属性,需要继续向下派生子类
 */
abstract class BasicAuthDetails : UserDetails {

    /**
     * 用户权限列表
     */
    private var authorities: Collection<GrantedAuthority>? = null

    @TableField(exist = false)
    private var password: String? = null

    @TableField(exist = false)
    private var username: String? = null

    @TableField(exist = false)
    private var accountNonExpired = true

    @TableField(exist = false)
    private var accountNonLocked = true

    @TableField(exist = false)
    private var credentialsNonExpired = true

    @TableField(exist = false)
    private var enabled = true

    @TableField(exist = false)
    var permissions: List<String>? = null
    fun setEnabled(enabled: Boolean) {
        this.enabled = enabled
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    fun setUsername(username: String?) {
        this.username = username
    }

    fun setAccountNonExpired(accountNonExpired: Boolean) {
        this.accountNonExpired = accountNonExpired
    }

    fun setAccountNonLocked(accountNonLocked: Boolean) {
        this.accountNonLocked = accountNonLocked
    }

    fun setCredentialsNonExpired(credentialsNonExpired: Boolean) {
        this.credentialsNonExpired = credentialsNonExpired
    }

    fun setAuthorities(authorities: Collection<GrantedAuthority>?) {
        this.authorities = authorities
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities!!
    }

    override fun getPassword(): String {
        return password!!
    }

    override fun getUsername(): String {
        return username!!
    }

    override fun isAccountNonExpired(): Boolean {
        return accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsNonExpired
    }

    override fun isEnabled(): Boolean {
        return enabled
    }
}