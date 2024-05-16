package cn.toutatis.xvoid.spring.security.security.config.handler;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Toutatis_Gc
 * @date 2022/11/16 14:32
 */
@Component
public class RequestMethodResolver {

    public RequestMethodHandler resolveMethod(HttpServletRequest request, HttpServletResponse response){
        return (getHandler,postHandler,defaultHandler) ->{
            String method = request.getMethod();
            switch (method){
                case "GET":
                    getHandler.run();
                    break;
                case "POST":
                    postHandler.run();
                    break;
                default:
                    if (defaultHandler != null){
                        defaultHandler.run();
                    }
                    break;
            }
        };
    }

}
