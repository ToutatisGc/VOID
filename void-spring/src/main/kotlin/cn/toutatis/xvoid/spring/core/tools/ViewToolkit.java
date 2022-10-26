package cn.toutatis.xvoid.spring.core.tools;

/**
 * @author Toutatis_Gc
 * @date 2022/10/26 19:38
 * View访问路径复杂,减少路径拼接
 */
public class ViewToolkit {

    private final String basePath;

    public ViewToolkit(String basePath) {
        this.basePath = basePath;
    }

    /**
     * 拼接路径
     * @param routePath 路由地址
     * @return 完整路由地址
     */
    public String toView(String routePath){
        return basePath + "/" + routePath;
    }

}
