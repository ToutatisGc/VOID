package cn.toutatis.xvoid.spring.core.route.portal;

import cn.toutatis.xvoid.common.result.ProxyResult;
import cn.toutatis.xvoid.common.result.Result;
import cn.toutatis.xvoid.common.standard.StandardFields;
import cn.toutatis.xvoid.spring.core.tools.ViewToolkit;
import cn.toutatis.xvoid.spring.annotations.application.VoidController;
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration;
import cn.toutatis.xvoid.sqlite.SQLiteShell;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 根路径控制器
 * @author Toutatis_Gc
 * @date 2022/10/26 10:10
 */
@VoidController
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

    @Autowired
    private SQLiteShell sqLiteShell;

    @RequestMapping(value = "/handleServiceConfiguration",method = RequestMethod.GET)
    public Result handleServiceConfiguration(){
        JSONObject info = new JSONObject();
        Map<String, Object> map = sqLiteShell.selectOneMap("SELECT * FROM VOID_CONTEXT WHERE KEY = 'AES_SECRET' AND MCH_ID = '%s'".formatted(StandardFields.VOID_BUSINESS_DEFAULT_CREATOR));
        info.put("isPlatform", voidGlobalConfiguration.getPlatformMode());
        info.put("version", voidGlobalConfiguration.getVersion().getVersion());
        return new ProxyResult(info);
    }

    @RequestMapping(value = "/example",method = RequestMethod.GET)
    public ModelAndView example(){
        ModelAndView modelAndView = new ModelAndView("pages/dev/Example");
        modelAndView.addObject("title","示例");
        return modelAndView;
    }

}
