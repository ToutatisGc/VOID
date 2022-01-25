package cn.toutatis.vertx

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router

class VoidVerticle : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {
//    log.info("")

    val router: Router = Router.router(vertx)
    val config: JsonObject = config()

    vertx
      .createHttpServer()
      .requestHandler(router)
      .requestHandler { req ->
        req.response()
          .putHeader("content-type", "text/plain;charset=UTF-8")
          .end("Hello from Vert.x! 啊阿斯顿阿萨大1111","UTF-8")
      }
      .listen(config.getInteger("http.port",8080)) { http ->
        if (http.succeeded()) {
          startPromise.complete()
          println("HTTP server started on port 8888")
        } else {
          startPromise.fail(http.cause());
        }
      }
  }
}
