package cn.toutatis.xvoid.spring.core.security.handler;

/**
 * @author Toutatis_Gc
 * @date 2022/11/16 14:28
 * 所有拦截器应处理所有请求方法
 */
@FunctionalInterface
public interface RequestMethodHandler {

    /**
     * 精简请求为GET/POST
     * @param getHandler GET方法处理
     * @param postHandler POST方法处理
     * @param defaultHandler 默认方法处理
     */
    void handle(Runnable getHandler, Runnable postHandler, Runnable defaultHandler);

}
