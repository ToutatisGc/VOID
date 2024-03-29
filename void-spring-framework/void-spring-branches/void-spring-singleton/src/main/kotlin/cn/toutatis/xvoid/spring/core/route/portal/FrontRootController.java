package cn.toutatis.xvoid.spring.core.route.portal;

import cn.toutatis.xvoid.spring.core.tools.ViewToolkit;
import cn.toutatis.xvoid.spring.annotations.application.VoidController;
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 根路径控制器
 * @author Toutatis_Gc
 * @date 2022/10/26 10:10
 */
@RestController
@Transactional(rollbackFor = Exception.class)
public class FrontRootController {

    @Value("${spring.application.name}")
    private String applicationName;

    private static final String BASE_PATH = "pages/portal";

    private final ViewToolkit viewToolkit = new ViewToolkit(BASE_PATH);

    private final VoidGlobalConfiguration voidGlobalConfiguration;

    private final VoidGlobalConfiguration.GlobalServiceConfig globalServiceConfig;

    public FrontRootController(VoidGlobalConfiguration voidGlobalConfiguration) {
        this.voidGlobalConfiguration = voidGlobalConfiguration;
        globalServiceConfig = voidGlobalConfiguration.getGlobalServiceConfig();
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
