package cn.toutatis.xvoid.spring.core.security.config;

import cn.toutatis.xvoid.orm.base.authentication.entity.derive.AuthInfo;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkitKt;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


/**
 * 安全上下文工具类
 * @author Toutatis_Gc
 * @since  0.0.0-ALPHA
 */
@Component
public class SecurityContextToolkit {

    private final Logger logger = LoggerToolkit.getLogger(this.getClass());

    /**
     * 获取当前登录用户
     * @param c 具体的用户类型
     * @param <T> 用户类型
     * @return 用户信息
     */
    public <T extends AuthInfo> T getCurrentLoginUser(Class<T> c){
        Object principal = getPrincipal();
        if (principal instanceof String){return null;}
        return c.cast(principal);
    }

    /**
     * @return 当前登录用户的认证信息
     */
    public AuthInfo getCurrentLoginUser(){
        Object principal = getPrincipal();
        if (principal instanceof String){return null;}
        return (AuthInfo) principal;
    }

    /**
     * @return 获取当前登录用户的安全上下文
     */
    public Authentication getSecurityAuthentication(){
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null){
            return context.getAuthentication();
        }else {
            String error = LoggerToolkitKt.errorWithModule(logger, "SECURITY", "安全上下文缺失");
            throw new NullPointerException(error);
        }
    }

    /**
     * @return 获取当前登录用户的认证信息
     */
    public Object getPrincipal(){
        return getSecurityAuthentication().getPrincipal();
    }

}
