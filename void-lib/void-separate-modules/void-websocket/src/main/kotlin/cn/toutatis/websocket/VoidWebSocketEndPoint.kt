package cn.toutatis.websocket

import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.server.ServerEndpoint


/**
 *@author Toutatis_Gc
 *@date 2022/4/15 17:20
 */
@ServerEndpoint("/websocket")
class VoidWebSocketEndPoint {

    @OnOpen
    fun openSocket(x: Session, y: Any): Unit {

    }

}