package cn.toutatis.xvoid.spring.support.core.aop.filters

import org.springframework.stereotype.Component
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletRequest

@WebFilter(filterName = "RequestCorsFilter", description = "请求跨域过滤器")
class RequestCorsFilter : Filter{

    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        /*TODO 跨域处理器*/
        val httpServletRequest = request as HttpServletRequest
        System.err.println(httpServletRequest.getHeader("Cookie"))
        val headerNames = httpServletRequest.headerNames
        /*TODO 开放HEADERS*/
//        for (headerName in headerNames) {
//            System.err.println(headerName)
//        }
        filterChain.doFilter(request, response)
    }

}