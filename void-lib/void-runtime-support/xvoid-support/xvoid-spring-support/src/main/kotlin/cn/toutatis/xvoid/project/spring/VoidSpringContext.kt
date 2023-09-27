package cn.toutatis.xvoid.project.spring

import cn.toutatis.xvoid.context.VoidContext
import org.springframework.context.ConfigurableApplicationContext

class VoidSpringContext : VoidContext {
    constructor()
    constructor(context: ConfigurableApplicationContext){
        this.context = context
    }

    companion object{
        public var properties: VoidSpringProperties = VoidSpringProperties()
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
        val displayName = context.displayName
        System.err.println(displayName)
    }
}