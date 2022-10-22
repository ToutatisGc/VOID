package cn.toutatis.vertx

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class VoidVerticle : AbstractVerticle() {

  companion object{
    private val logger : Logger = LoggerFactory.getLogger(VoidVerticle::class.java)
  }

  override fun start(startPromise: Promise<Void>) {
    /*异常处理*/
    logger.info("")
    val router: Router = Router.router(vertx)

    router.route().failureHandler { handler ->
      run {
        logger.error(handler.failure().message)
        val statusCode = 404
      }
    }

//    router.route("")

    /*配置*/
    val config: JsonObject = config()

    vertx
      .createHttpServer()
      .requestHandler(router)
      .requestHandler { req ->
        req.response()
          .putHeader("content-type", "text/plain;charset=UTF-8")
          .end("Hello from Vert.x! 啊阿斯顿阿萨大1111asd","UTF-8")
      }
      .listen(config.getInteger("http.port",20080)) { http ->
        if (http.succeeded()) {
          startPromise.complete()
          println("HTTP server started on port 20080")
        } else {
          startPromise.fail(http.cause());
        }
      }
  }
}
