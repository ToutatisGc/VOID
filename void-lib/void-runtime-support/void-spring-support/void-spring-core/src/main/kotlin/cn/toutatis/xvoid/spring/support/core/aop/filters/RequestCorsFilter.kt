package cn.toutatis.xvoid.spring.support.core.aop.filters

import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest

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