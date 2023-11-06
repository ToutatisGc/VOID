package cn.toutatis.xvoid.project.spring

import cn.toutatis.xvoid.common.standard.StandardComponentPool
import cn.toutatis.xvoid.context.VoidContext
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.infoWithModule
import org.springframework.context.ConfigurableApplicationContext

class VoidSpringContext : VoidContext {

    private val logger = LoggerToolkit.getLogger(javaClass)

    constructor()
    constructor(context: ConfigurableApplicationContext){
        this.context = context
    }

    companion object{
        @JvmStatic
        public var contextVariables: VoidSpringContextVariables = VoidSpringContextVariables()
    }

    private var context:ConfigurableApplicationContext? = null

    fun intervene(){
        if (context != null){
            this.intervene(context = this.context!!)
        }else{
            throw NullPointerException("Context is null")
        }
    }

    override fun intervene(context: Any) {
        if (context is ConfigurableApplicationContext){
            this.intervene(context)
        }
    }

    private fun intervene(context: ConfigurableApplicationContext) {
        logger.infoWithModule(Meta.MODULE_NAME,Meta.SUB_MODULE_NAME,"已介入[${context.id}]应用环境...")
        val info = context.getBean(StandardComponentPool.VOID_CONTEXT_VARIABLES)
        System.err.println(info)
    }
}