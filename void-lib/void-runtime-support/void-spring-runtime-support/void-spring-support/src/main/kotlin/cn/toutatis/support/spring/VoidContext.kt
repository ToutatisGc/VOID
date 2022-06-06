package cn.toutatis.support.spring

import cn.toutatis.common.annotations.database.AutoCreateDataObject
import cn.toutatis.common.face.VoidContextInterface
import cn.toutatis.support.spring.beans.AutoInsertDatabaseComponent
import cn.toutatis.support.spring.config.VoidContextConfigBuilder
import cn.toutatis.toolkit.clazz.ClassScanner
import cn.toutatis.toolkit.log.LoggerToolkit
import org.springframework.context.ConfigurableApplicationContext


object VoidContext : VoidContextInterface {

    lateinit var needCreatedBeans: Set<Class<*>>

    private val logger = LoggerToolkit.getLogger(VoidContext::class.java)

    fun init(config: VoidContextConfigBuilder.VoidConfiguration) {
        this.init()
        if(config.autoCreateDataObject){
            val autoInsertObjs = ClassScanner.scan(config.basePackages, AutoCreateDataObject::class.java)
            needCreatedBeans = autoInsertObjs
        }
    }

    override fun init() {}


    /**
     * 介入
     */
    fun intervene(applicationContext: ConfigurableApplicationContext){
        val autoInitDatabase = applicationContext.getBean(AutoInsertDatabaseComponent::class.java)
        autoInitDatabase.checkAndInsert()
        autoInitDatabase.pushField()
    }

//    private fun getYmlProperties(key:String){
//
//    }

}