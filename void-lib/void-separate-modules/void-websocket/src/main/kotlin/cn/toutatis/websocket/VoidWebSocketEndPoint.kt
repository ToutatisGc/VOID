package cn.toutatis.websocket

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.IOException
import javax.websocket.*
import javax.websocket.server.ServerEndpoint


/**
 * @author Toutatis_Gc
 * @date 2022/4/15 17:20
 *
 */
@Component
@ServerEndpoint("/websocket")
class VoidWebSocketEndPoint  {

    private val logger = LoggerFactory.getLogger(VoidWebSocketEndPoint::class.java)

    @OnOpen
    @Throws(IOException::class)
    fun onOpen(session: Session) {
        logger.info("新连接")
    }

    @OnClose
    fun onClose() {
        logger.debug("连接关闭")
    }

    @OnMessage
    @Throws(IOException::class)
    fun onMessage(message: String, session: Session?) {
        logger.info("收到消息${message}")
    }

    @OnError
    fun onError(session: Session?, error: Throwable) {
        error.printStackTrace()
    }

}