package cn.toutatis.xvoid.support

import cn.toutatis.xvoid.common.annotations.database.AutoCreateDataObject
import cn.toutatis.xvoid.common.face.VoidContextInterface
import cn.toutatis.xvoid.support.spring.enhance.AutoInsertDatabaseComponent
import cn.toutatis.xvoid.support.spring.config.VoidContextConfigBuilder
import cn.toutatis.xvoid.toolkit.clazz.AnnotationToolkit
import cn.toutatis.xvoid.toolkit.clazz.ClassScanner
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
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
        init()
        if(config.autoCreateDataObject){
            val autoInsertObjs = ClassScanner.scan(config.basePackages, CREATE_BEAN_ANNOTATION )
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