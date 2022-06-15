package cn.toutatis.support.spring.aop.filters

import cn.hutool.core.util.IdUtil
import cn.toutatis.common.standard.StandardFields
import cn.toutatis.support.spring.config.VoidConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.servlet.*
import javax.servlet.annotation.WebFilter

@Component
@WebFilter(
    filterName = "RequestRidInjectFilter",
    description = "请求ID注入过滤器"
)
class RequestRidInjectFilter : Filter {

    @Autowired
    private lateinit var voidConfiguration: VoidConfiguration

    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        val recordRequestId = voidConfiguration.globalLogConfig.recordRequestId
        if (recordRequestId){
            request.setAttribute(StandardFields.FILTER_REQUEST_ID,IdUtil.fastUUID());
        }
        filterChain.doFilter(request,response)
    }

}