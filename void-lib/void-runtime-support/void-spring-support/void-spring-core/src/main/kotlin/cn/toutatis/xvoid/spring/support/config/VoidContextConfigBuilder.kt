package cn.toutatis.xvoid.spring.support.config


/**
 * @author Toutatis_Gc
 * @date 2022/6/2
 * VOID框架增强
 */
class VoidContextConfigBuilder {

    /**
     * 扫描选定包下的类，自动将带有AutoCreateDataObject注解的类映射至数据库
     * @see cn.toutatis.common.annotations.AutoCreateDataObject
     */
    private var autoCreateDataObject: Boolean = true

    private var basePackages = "cn.toutatis"

    fun setAutoCreateDataObject(autoCreateDataObject:Boolean): VoidContextConfigBuilder {
        this.autoCreateDataObject = autoCreateDataObject
        return this
    }

    fun setBasePackages(basePackages:String): VoidContextConfigBuilder {
        this.basePackages = basePackages
        return this
    }

    data class VoidConfiguration(
        val autoCreateDataObject: Boolean,
        val basePackages:String
    ) {}

    fun build():VoidConfiguration{
        return VoidConfiguration(autoCreateDataObject,basePackages)
    }

}