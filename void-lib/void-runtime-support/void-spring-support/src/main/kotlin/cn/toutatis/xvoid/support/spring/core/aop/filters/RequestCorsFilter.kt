package cn.toutatis.xvoid.support.spring.core.aop.filters

import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter

@Component
@WebFilter(
    filterName = "RequestCorsFilter",
    description = "请求跨域过滤器"
)
class RequestCorsFilter : Filter{

    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        /*TODO 跨域处理器*/
        filterChain.doFilter(request, response)
    }

}