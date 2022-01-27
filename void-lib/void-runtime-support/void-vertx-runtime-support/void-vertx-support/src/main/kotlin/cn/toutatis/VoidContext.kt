package cn.toutatis

import io.vertx.core.Verticle
import io.vertx.core.Vertx

class VoidContext(
    private var vertx: Vertx,
    private var verticle: Class<out Verticle>) {

    companion object{

        private lateinit var voidContext : VoidContext

        fun getContext(): Unit {

        }

    }

    init {

    }

    fun run(): Unit {
        val verticleName = verticle.name

    }

}

