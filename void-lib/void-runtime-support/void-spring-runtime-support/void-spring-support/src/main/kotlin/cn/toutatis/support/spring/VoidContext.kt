package cn.toutatis.support.spring

import cn.toutatis.common.annotations.database.AutoCreateDataObject
import cn.toutatis.common.face.VoidContextInterface
import cn.toutatis.support.spring.beans.AutoInsertDatabaseComponent
import cn.toutatis.support.spring.config.VoidContextConfigBuilder
import cn.toutatis.toolkit.clazz.AnnotationToolkit
import cn.toutatis.toolkit.clazz.ClassScanner
import cn.toutatis.toolkit.log.LoggerToolkit
import org.springframework.context.ConfigurableApplicationContext

/**
 * @author Toutatis_Gc
 * VOID上下文注入点
 */
object VoidContext : VoidContextInterface {

    lateinit var needCreatedBeans: Set<Class<*>>

    private val logger = LoggerToolkit.getLogger(VoidContext::class.java)

    private val CREATE_BEAN_ANNOTATION = AutoCreateDataObject::class.java

    fun init(config: VoidContextConfigBuilder.VoidConfiguration) {
        this.init()
        if(config.autoCreateDataObject){
            val autoInsertObjs = ClassScanner.scan(config.basePackages,CREATE_BEAN_ANNOTATION )
            needCreatedBeans = autoInsertObjs
            if (needCreatedBeans.isNotEmpty()){
                needCreatedBeans.forEach {
                    val dataObject = it.getAnnotation(CREATE_BEAN_ANNOTATION)
                    val tableName =
                        AnnotationToolkit.getArrFirstString(dataObject, "tableName")
                            ?: it.name.substring(it.name.lastIndexOf('.')+1)
                    System.err.println(tableName)
                }
            }else{
                logger.warn("请检查需要生成的实体类所带注解@AutoCreateDataObject当前未找到实体类")
            }
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