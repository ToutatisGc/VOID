package cn.toutatis.xvoid.toolkit.http

import jakarta.servlet.http.HttpServletRequest
import java.net.InetAddress
import java.net.UnknownHostException

//import javax.servlet.http.HttpServletRequest

/**
 * @author Toutatis_Gc
 * Servlet请求工具类
 */
object RequestToolkit {

    /**
     * 首先明确request.getRemoteAddr()这块不能要。一看就是内网ip，不然直接锁死了。
     * 在jax的告诫"去掉这块代码，让其为空则不禁用，否则内网锁定ip，
     * 且很可能是用户微服务集群2台的ip，那就都不能登录了"下，我删掉了这块代码。
     * 且最终咨询了运维之后发现这个固定的192.168.1.10正是开发环境的K8S地址!那确实不能返回！
     * realIp固定的115.1.2.3问了运维大哥后知道了是真实ip地址！不是内网地址！且是公司的网关地址。
     * 那我明白了，我连公司vpn及我公司电脑其实都是连的公司wifi，所以最终请求发出去都是走的公司网关，此网关不是web项目的网关！
     * 所以最终地址都一致。那这么看满足需求，1个人连的1个wifi，不能连续输错密码5次否则ip禁用，用其他方式连wifi也一样。
     * “X-Forwarded-For”第一个值与其realIo一样应该是我只有1个代理。这边也确实只有Nginx代理了。
     * 所以“X-Forwarded-For”第二个值开始则是代理服务器的地址。
     * 咨询了jax和让运维看了后发现是k8s的一些容器的地址。
     * 只是这个地址为什么一直变jax也很奇怪。我测了下是每次都在变。
     * 所以原来方法是对的，只去获取forward的第一个值！
     * 原文链接：https://blog.csdn.net/m0_67390963/article/details/124777396
     *
     * X-Forwarded-For的字面意思是“为谁而转发”，
     * 形式上和“Via”差不多，也是每经过一个代理节点就会在字段里追加一个信息。
     * 但“Via”追加的是代理主机名（或者域名），而“X-Forwarded-For”追加的是请求方的 IP 地址。
     * 所以，在字段里最左边的 IP 地址就是客户端的地址。
     *
     * “X-Real-IP”是另一种获取客户端真实 IP 的手段，
     * 它的作用很简单，就是记录客户端 IP 地址，
     * 没有中间的代理信息，相当于是“X-Forwarded-For”的简化版。
     * 如果客户端和源服务器之间只有一个代理，那么这两个字段的值就是相同的。
     * @param request servlet请求对象
     * @return 获取真实ip地址,不返回内网地址
     */
    @JvmStatic
    fun getIpAddress(request: HttpServletRequest): String? {
        var ipAddress: String?
        try {
            ipAddress = request.getHeader("x-forwarded-for")
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equals(ipAddress, ignoreCase = true)) {
                ipAddress = request.getHeader("Proxy-Client-IP")
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equals(ipAddress, ignoreCase = true)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP")
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equals(ipAddress, ignoreCase = true)) {
                ipAddress = request.remoteAddr
                if (ipAddress == "127.0.0.1") {
                    var inet: InetAddress? = null
                    try {
                        inet = InetAddress.getLocalHost()
                    } catch (e: UnknownHostException) {
                        e.printStackTrace()
                    }
                    ipAddress = inet!!.hostAddress
                }
            }
            if (ipAddress != null && ipAddress.length > 15) {
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","))
                }
            }
        } catch (e: Exception) {
            ipAddress = ""
        }
        return ipAddress
    }

}