package cn.toutatis.xvoid.spring.core.route.base;

import cn.toutatis.xvoid.common.Version;
import cn.toutatis.xvoid.common.result.ProxyResult;
import cn.toutatis.xvoid.common.result.Result;
import cn.toutatis.xvoid.common.result.ResultCode;
import cn.toutatis.xvoid.spring.annotations.application.VoidController;
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration;
import cn.toutatis.xvoid.spring.configure.system.VoidSecurityConfiguration;
import cn.toutatis.xvoid.sqlite.SQLiteShell;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 根路径控制器
 * @author Toutatis_Gc
 * @date 2022/10/26 10:10
 */
@VoidController
@RequestMapping("/init")
public class RootInitController {

    private final VoidGlobalConfiguration voidGlobalConfiguration;

    private final VoidGlobalConfiguration.GlobalServiceConfig globalServiceConfig;

    private final VoidSecurityConfiguration securityConfiguration;

    private final SQLiteShell sqLiteShell;

    public RootInitController(VoidGlobalConfiguration voidGlobalConfiguration, SQLiteShell sqLiteShell, VoidSecurityConfiguration securityConfiguration) {
        this.voidGlobalConfiguration = voidGlobalConfiguration;
        globalServiceConfig = voidGlobalConfiguration.getGlobalServiceConfig();
        this.sqLiteShell = sqLiteShell;
        this.securityConfiguration = securityConfiguration;
    }


    @RequestMapping(value = "/handleServiceConfiguration",method = RequestMethod.GET)
    public Result handleServiceConfiguration(Version version){
        if (version == null){return new ProxyResult(ResultCode.MISSING_PARAMETER);}

        System.err.println(version);
        JSONObject info = new JSONObject();
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
