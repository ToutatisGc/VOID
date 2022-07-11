package cn.toutatis.xvoid.spring.core.security.handler;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 拦截器对象,包含ant匹配规则
 * @author Toutatis_Gc
 * @since 2020-10-08
 * @version 0
 */
public class InterceptorPattern {

    private HandlerInterceptor interceptor;

    private String pattern;

    private InterceptorPattern(){}

    public InterceptorPattern(HandlerInterceptor interceptor, String pattern) {
        this.interceptor = interceptor;
        this.pattern = pattern;
    }

    public HandlerInterceptor getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(HandlerInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
