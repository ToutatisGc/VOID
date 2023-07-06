package cn.toutatis.xvoid.support.spring.core.aop.filters

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component


@Component
@WebFilter(filterName = "RequestCorsFilter", description = "请求跨域过滤器")
class RequestCorsFilter : Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        /*TODO 跨域处理器*/
        val httpServletRequest = request as HttpServletRequest
        val headerNames = httpServletRequest.headerNames
        for (headerName in headerNames) {
            System.err.println(headerName)
        }
        filterChain.doFilter(request, response)
    }

}