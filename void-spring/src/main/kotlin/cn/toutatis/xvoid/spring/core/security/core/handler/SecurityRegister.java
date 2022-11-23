package cn.toutatis.xvoid.spring.core.security.core.handler;



import cn.toutatis.xvoid.support.spring.config.VoidConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Toutatis_Gc
 */
@Component
public class SecurityRegister {

    private Logger logger = LoggerFactory.getLogger(SecurityRegister.class);

    @Autowired
    private VoidConfiguration voidConfiguration;

    //外部服务拦截器 START

    /**
     * 外部接口拦截器
     */
//    @Autowired
//    private SeparationInterceptor separationInterceptor;
//
//    @Autowired
//    private JustShowInterceptor justShowInterceptor;

    // 外部服务拦截器 END

    /**
     * 外部接口服务过滤器链
     */
    private List<InterceptorPattern> outerServiceInterceptor = new ArrayList<>(2);

    @PostConstruct
    public void init(){
//        外部接口注册
//        outerServiceInterceptor.add(new InterceptorPattern(separationInterceptor,"/**/external/**"));
//        if (voidConfiguration.getShowMode()){
//            outerServiceInterceptor.add(new InterceptorPattern(justShowInterceptor,"/**"));
//        }
    }

    public void registeredOuterControllerInterceptor(InterceptorRegistry registry){
        outerServiceInterceptor.forEach(interceptor -> {
            String pattern = interceptor.getPattern();
            HandlerInterceptor handlerInterceptor = interceptor.getInterceptor();
            registry.addInterceptor(handlerInterceptor).addPathPatterns(pattern);
            logger.info("[添加外部接口过滤器链]{},antPath规则:{}",handlerInterceptor.getClass().getName(),pattern);
        });
    }

}
