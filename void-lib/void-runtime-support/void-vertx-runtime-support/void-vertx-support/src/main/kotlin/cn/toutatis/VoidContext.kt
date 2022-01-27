package cn.toutatis

import cn.toutatis.annotation.VoidVertxApplication
import cn.toutatis.bean.factory.PrepareBeanFactory
import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.AsyncResult
import io.vertx.core.Verticle
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import org.slf4j.LoggerFactory


class VoidContext(private var vertx: Vertx, private var verticle: Class<out Verticle>) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    /**
     * get main class
     */
    private fun getVoidApplicationClass(): Class<*>? {
        val classArray = Exception().stackTrace
        var applicationClass : Class<*>? = null
        for (i in classArray.indices) {
            val methodName = classArray[i].methodName
            if (methodName == "main"){
                val classname = classArray[i].className
                val mainClass: Class<*> = Class.forName(classname)
                val isApp = mainClass.getDeclaredAnnotation(VoidVertxApplication::class.java)
                if (isApp != null){
                    applicationClass = mainClass
                    break
                }
            }
        }
        return applicationClass
    }

    /**
     * this method is init context,check runtime environment and put necessary beans into context.
     */
    private fun start(): Unit {
        val voidApplicationClass = this.getVoidApplicationClass()
            ?: throw NullPointerException("Not found main class with annotation VoidVertxApplication")

        val prepareBeanFactory = PrepareBeanFactory(voidApplicationClass)
    }

    fun run(): Unit {

        this.start()

        val retrieverOptions = ConfigRetrieverOptions()
        val config = ConfigStoreOptions()
            .setType("file")
            .setOptional(true)
            .setConfig(JsonObject().put("path", "application.json"))
        retrieverOptions.addStore(config)
        val yamlConfig = ConfigStoreOptions()
            .setType("file")
            .setFormat("yaml")
            .setOptional(true)
            .setConfig(JsonObject().put("path", "application.yaml"))
        retrieverOptions.addStore(yamlConfig)
        val retriever = ConfigRetriever.create(vertx, retrieverOptions)
        retriever.getConfig { ar: AsyncResult<JsonObject?> ->
            if (ar.failed()) {
                // Failed to retrieve the configuration
            } else {
                val verticleName: String = verticle.name
                vertx.deployVerticle(verticleName)
                val result = ar.result()
                System.err.println(result)
            }
        }
    }

}

