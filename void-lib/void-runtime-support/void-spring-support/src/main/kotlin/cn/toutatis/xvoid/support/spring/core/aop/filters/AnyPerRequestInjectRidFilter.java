package cn.toutatis.xvoid.support.spring.core.aop.filters;

import cn.hutool.core.util.IdUtil;
import cn.toutatis.xvoid.common.standard.StandardFields;
import cn.toutatis.xvoid.support.spring.config.VoidConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Toutatis_Gc
 * @date 2022/11/13 18:14
 */
@Component
@Order(-100)
public class AnyPerRequestInjectRidFilter extends OncePerRequestFilter {

    private final VoidConfiguration voidConfiguration;

    public AnyPerRequestInjectRidFilter(VoidConfiguration voidConfiguration) {
        this.voidConfiguration = voidConfiguration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (voidConfiguration.getGlobalLogConfig().getRecordRequestId()){
            request.setAttribute(StandardFields.FILTER_REQUEST_ID_KEY, IdUtil.fastUUID());
        }
        filterChain.doFilter(request,response);
    }

}
