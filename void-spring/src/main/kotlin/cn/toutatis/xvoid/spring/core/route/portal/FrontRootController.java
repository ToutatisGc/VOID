package cn.toutatis.xvoid.spring.core.route.portal;

import cn.toutatis.xvoid.spring.core.tools.ViewToolkit;
import cn.toutatis.xvoid.support.spring.annotations.VoidController;
import cn.toutatis.xvoid.support.spring.config.VoidConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Toutatis_Gc
 * @date 2022/10/26 10:10
 */
@VoidController
public class FrontRootController {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String BASE_PATH = "pages/portal";

    private final ViewToolkit viewToolkit;

    private final VoidConfiguration.GlobalServiceConfig globalServiceConfig;

    private final VoidConfiguration voidConfiguration;

    public FrontRootController(VoidConfiguration voidConfiguration) {
        this.voidConfiguration = voidConfiguration;
        globalServiceConfig = voidConfiguration.getGlobalServiceConfig();
        viewToolkit = new ViewToolkit(BASE_PATH);
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView(viewToolkit.toView("Index"));
        modelAndView.addObject("appName",applicationName);
        modelAndView.addObject("title","首页");
        return modelAndView;
    }

    @RequestMapping(value = "/example",method = RequestMethod.GET)
    public ModelAndView example(){
        ModelAndView modelAndView = new ModelAndView("pages/dev/Example");
        modelAndView.addObject("title","示例");
        return modelAndView;
    }

}
