package cn.toutatis.xvoid.spring.core.security.config;


import cn.toutatis.xvoid.spring.business.user.entity.AuthInfo;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Toutatis_Gc
 */
@Component("AntUrlService")
public class AntUrlService {

    private final Logger logger =  LoggerToolkit.getLogger(AntUrlService.class);

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * @param request 请求
     * @param authentication 认证
     * @return 是否有权限
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
//        System.err.println(request.getRequestURI());
        Object authInfo = authentication.getPrincipal();
        if (authInfo instanceof String){ return false; }
        List<String> patterns;
        if (authInfo instanceof AuthInfo){
            patterns = ((AuthInfo) authInfo).getPermissions();
        }else{
            logger.error("authInfo is not LoginInfo "+authInfo);
            return false;
        }
        boolean matches = false;
        for (String pattern : patterns) {
            matches = antPathMatcher.match(pattern, request.getRequestURI());
            if (matches){ break; }
        }
        return matches;
    }
}
