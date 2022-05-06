package cn.toutatis.websocket.support.spring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.server.standard.ServerEndpointExporter

/**
 *@author Toutatis_Gc
 *@date 2022/4/15 17:36
 */

@Configuration
class VoidSpringWebsocketSupportConfiguration {

    @Bean
    fun getServerEndpointExporter(): ServerEndpointExporter {
        return ServerEndpointExporter()
    }

}