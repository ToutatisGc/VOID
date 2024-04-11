package cn.toutatis.xvoid.spring.support.toolkits

/**
 * 容器工具
 * @author Toutatis_Gc
 */
object ContainerToolkit {


    /**
     * 判断是否为嵌入式Servlet容器
     */
    @JvmStatic
    fun isEmbeddedServlet():Boolean{
        return try {
            Class.forName("org.springframework.boot.web.embedded.tomcat.EmbeddedWebApplicationContext")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }

}