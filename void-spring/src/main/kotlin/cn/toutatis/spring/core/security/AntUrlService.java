package cn.toutatis.spring.core.security;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Toutatis_Gc
 */
@Component("AntUrlService")
public class AntUrlService {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        System.err.println("AntUrlService.hasPermission");
        return true;
//        String remoteCall = request.getHeader(VoidHeader.VOID_REMOTE_CALL);
//        if ("FEIGN".equals(remoteCall)){
//            String remoteHost = request.getRemoteHost();
//            for (String pattern : allowHost) {
//                if (Pattern.matches(pattern, remoteHost)){
//                    return true;
//                }
//            }
//        }
//        Object authInfo = authentication.getPrincipal();
//        List<String> patterns = null;
//        if (authInfo instanceof AccountEntity){
//            AccountEntity accountEntity = (AccountEntity) authInfo;
//            AccountPermissionEnum accountPermissionEnum = accountEntity.getAccountPermissionEnum();
//            switch (accountPermissionEnum){
//                case REMOTE_DOUBLE_CHECK:
//                    List<JSONObject> remoteDoubleCheckPermissionList = accountEntity.getRemoteDoubleCheckPermissionList();
//                    ArrayList<String> remotePermissions = new ArrayList<>(remoteDoubleCheckPermissionList.size());
//                    remoteDoubleCheckPermissionList.stream().iterator().forEachRemaining(item ->{
//                        String antUrl = item.getString("antUrl");
//                        remotePermissions.add(antUrl);
//                    });
//                    patterns = remotePermissions;
//                    break;
//                case BUSINESS:
//                case DEV:
//                    patterns = accountEntity.getPermissions();
//                    break;
//                default:
//                    return false;
//            }
//        }else if (authInfo instanceof SecretEntity){
//            SecretEntity secretEntity = (SecretEntity) authInfo;
//            patterns = secretEntity.getPermissions();
//        }else return false;
//
//        if (patterns == null || patterns.size() == 0) return false;
//
//        boolean match = false;
//        for (String pattern : patterns) {
//            String requestURI = request.getRequestURI();
//            match = antPathMatcher.match(pattern, requestURI);
//            if (match) break;
//        }
//        return match;
    }
}
