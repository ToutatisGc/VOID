package cn.toutatis.xvoid.spring.support.core.aop.filters;

import cn.hutool.core.util.IdUtil;
import cn.toutatis.xvoid.common.standard.StandardFields;
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 注入请求ID的过滤器
 * @author Toutatis_Gc
 * @date 2022/11/13 18:14
 */
@Component
@Order(-100)
public class AnyPerRequestInjectRidFilter extends OncePerRequestFilter {

    private final VoidGlobalConfiguration voidGlobalConfiguration;

    public AnyPerRequestInjectRidFilter(VoidGlobalConfiguration voidGlobalConfiguration) {
        this.voidGlobalConfiguration = voidGlobalConfiguration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        this.fillRequestId(request, response);
        filterChain.doFilter(request,response);
    }

    public void fillRequestId(HttpServletRequest request, HttpServletResponse response){
        if (voidGlobalConfiguration.getGlobalLogConfig().getRecordRequestId()){
            request.setAttribute(StandardFields.FILTER_REQUEST_ID_KEY, IdUtil.fastUUID());
        }
    }

}
