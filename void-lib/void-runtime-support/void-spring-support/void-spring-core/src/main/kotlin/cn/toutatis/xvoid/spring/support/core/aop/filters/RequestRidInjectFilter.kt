package cn.toutatis.xvoid.spring.support.core.aop.filters

import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration
import org.springframework.beans.factory.annotation.Autowired
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse

/**
 * @see AnyPerRequestInjectRidFilter 参考此过滤器
 */
/*@Component
@WebFilter(
    filterName = "RequestRidInjectFilter",
    description = "请求ID注入过滤器",
    dispatcherTypes = [
        DispatcherType.ASYNC,DispatcherType.INCLUDE,DispatcherType.REQUEST, DispatcherType.ERROR,DispatcherType.FORWARD]
)*/
@Deprecated("ServletDispatcherType不生效,改用Spring的OncePerRequestFilter")
class RequestRidInjectFilter : Filter {

    @Autowired
    private lateinit var voidGlobalConfiguration: VoidGlobalConfiguration

    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
//        val recordRequestId = voidConfiguration.globalLogConfig.recordRequestId
//        if (recordRequestId){
//            request.setAttribute(StandardFields.FILTER_REQUEST_ID,IdUtil.fastUUID());
//        }
        filterChain.doFilter(request,response)
    }

}