package cn.toutatis.xvoid.toolkit.clazz


/**
 * Class toolkit
 * Class 工具类
 * @author Toutatis_Gc
 */
object ClassToolkit {

    /**
     * Is running from jar
     * 判断Class是否是否从jar运行
     * @param clazz Class类
     * @return true: 是jar运行 false: 非jar运行
     */
    @JvmStatic
    fun isRunningFromJar(clazz: Class<*>): Boolean {
        val protectionDomain = clazz.getProtectionDomain()
        val codeSource = protectionDomain.codeSource
        return codeSource != null && codeSource.location.toString().endsWith(".jar")
    }

}