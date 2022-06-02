package cn.toutatis.support.spring

import cn.toutatis.common.annotations.AutoCreateDataObject
import cn.toutatis.common.face.VoidContextInterface
import cn.toutatis.support.spring.config.VoidContextConfigBuilder
import cn.toutatis.toolkit.clazz.ClassScanner
import cn.toutatis.toolkit.log.LoggerToolkit
import org.springframework.context.ConfigurableApplicationContext


object VoidContext : VoidContextInterface {

    private val logger = LoggerToolkit.getLogger(VoidContext::class.java)

    fun init(config: VoidContextConfigBuilder.VoidConfiguration) {
        if(config.autoCreateDataObject){
            val autoInsertObjs = ClassScanner.scan(config.basePackages, AutoCreateDataObject::class.java)
            /*TODO 添加到数据库*/
//            autoInsertObjs.forEach {

//            }
        }
    }

    override fun init() {

    }


    /**
     * 介入
     */
    fun intervene(applicationContext: ConfigurableApplicationContext){

    }

}