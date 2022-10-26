package cn.toutatis.xvoid.spring.core.route;

import cn.toutatis.xvoid.support.spring.annotations.VoidController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Toutatis_Gc
 * @date 2022/10/26 10:10
 */
@VoidController
public class FrontRootController {

    @Value("${spring.application.name:VOID-SPRING | 春野}")
    private String applicationName;

    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("Index");
        modelAndView.addObject("applicationName",applicationName);
        return modelAndView;
    }

}
