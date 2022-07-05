package cn.toutatis.spring.core.security;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

public class RequestHolder implements ServletRequestListener {

    private static ThreadLocal<HttpServletRequest> httpServletRequestHolder =
            new ThreadLocal<HttpServletRequest>();
    
    @Override
    public void requestInitialized(ServletRequestEvent requestEvent) {
        HttpServletRequest request = (HttpServletRequest) requestEvent.getServletRequest();
        httpServletRequestHolder.set(request); // 绑定到当前线程
    }
    
    @Override
    public void requestDestroyed(ServletRequestEvent requestEvent) {
        httpServletRequestHolder.remove(); // 清理资源引用
    }
    
    public static HttpServletRequest getHttpServletRequest() {
        return httpServletRequestHolder.get();
    }
    
}