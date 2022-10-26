package cn.toutatis.xvoid.spring.core.route.portal;

import cn.toutatis.xvoid.spring.core.tools.ViewToolkit;
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

    private static final String BASE_PATH = "pages/portal";

    private final ViewToolkit viewToolkit;

    public FrontRootController() {
        viewToolkit = new ViewToolkit(BASE_PATH);
    }

    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView(viewToolkit.toView("Index"));
        modelAndView.addObject("applicationName",applicationName);
        modelAndView.addObject("title","首页");
        return modelAndView;
    }

    @RequestMapping("/example")
    public ModelAndView example(){
        ModelAndView modelAndView = new ModelAndView("Example");
        modelAndView.addObject("applicationName",applicationName);
        modelAndView.addObject("title","示例");
        return modelAndView;
    }

}
