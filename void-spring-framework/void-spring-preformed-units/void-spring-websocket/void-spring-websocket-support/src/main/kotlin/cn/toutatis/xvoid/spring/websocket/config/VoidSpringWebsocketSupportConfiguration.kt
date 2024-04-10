package cn.toutatis.xvoid.spring.websocket.config

import cn.toutatis.xvoid.spring.websocket.Meta
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.infoWithModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.server.standard.ServerEndpointExporter

/**
 * WebSocket支持
 * @author Toutatis_Gc
 * @date 2022/4/15 17:36
 */
@Configuration
@EnableWebSocket
class VoidSpringWebsocketSupportConfiguration {

    private val logger = LoggerToolkit.getLogger(javaClass)

    @Bean
    fun getServerEndpointExporter(): ServerEndpointExporter {
        val serverEndpointExporter = ServerEndpointExporter()
        logger.infoWithModule(Meta.MODULE_NAME,"已注册Tomcat-Spring-WebSocket支持.")
        return serverEndpointExporter
    }

}