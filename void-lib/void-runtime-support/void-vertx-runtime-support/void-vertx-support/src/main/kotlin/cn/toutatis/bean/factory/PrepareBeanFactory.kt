package cn.toutatis.bean.factory

import cn.toutatis.annotations.VoidVertxApplication
import cn.toutatis.utils.PackageUtil
import org.slf4j.LoggerFactory

class PrepareBeanFactory {

    private val logger = LoggerFactory.getLogger(PrepareBeanFactory::class.java)

    private var beanList = ArrayList<Class<*>>()

    constructor(mainClass: Class<*>){
        val className = PackageUtil.getClassName("cn.toutatis.vertx")
        System.err.println(className)
        val contextClassLoader: ClassLoader = Thread.currentThread().contextClassLoader
        val appAnnotation = mainClass.getDeclaredAnnotation(VoidVertxApplication::class.java)

        this.find(contextClassLoader,mainClass.`package`.name)

        val basePackages = appAnnotation.basePackages
        basePackages.forEach {

        }
    }

    private fun find(classLoader: ClassLoader,packageName: String): Unit {
        val urls = classLoader.getResources(packageName.replace(".", "/"))
        for (url in urls) {
            System.err.println(url.path)
        }
    }

    protected fun findBeans(){

    }

}