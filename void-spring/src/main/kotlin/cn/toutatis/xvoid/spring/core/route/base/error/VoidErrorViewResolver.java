package cn.toutatis.xvoid.spring.core.route.base.error;

import cn.toutatis.xvoid.Version;
import cn.toutatis.xvoid.common.standard.StandardFields;
import cn.toutatis.xvoid.data.common.result.ResultCode;
import cn.toutatis.xvoid.spring.core.tools.ViewToolkit;
import cn.toutatis.xvoid.support.spring.config.VoidConfiguration;
import cn.toutatis.xvoid.toolkit.constant.Time;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Toutatis_Gc
 * 错误页配置
 */
@Component
public class VoidErrorViewResolver implements ErrorViewResolver {

    @Value("${spring.application.name}")
    private String applicationName;

    private final Logger logger = LoggerToolkit.getLogger(this.getClass());

    private final VoidConfiguration.GlobalServiceConfig globalServiceConfig;

    private HttpServletResponse httpServletResponse;

    private final ViewToolkit viewToolkit = new ViewToolkit("pages/portal");

    public VoidErrorViewResolver(HttpServletResponse httpServletResponse, VoidConfiguration voidConfiguration) {
        this.httpServletResponse = httpServletResponse;
        this.globalServiceConfig = voidConfiguration.getGlobalServiceConfig();
    }


    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        String method = request.getMethod();
        ResultCode resultCode;
        HashMap<String, String> customInfo = new HashMap<>(3);
        switch (status){
            case NOT_FOUND:
                resultCode = ResultCode.NOT_FOUND;
                customInfo.put("message","您所访问的页面不存在,请稍后再试!");
                break;
            case INTERNAL_SERVER_ERROR:
                resultCode = ResultCode.REQUEST_EXCEPTION;
                customInfo.put("message","您的访问出错啦!请稍后再试或者刷新尝试!");
                break;
            default:
                logger.error("未发现异常处理,请尽快处理此类异常[{}]",status);
                resultCode = ResultCode.UNKNOWN_EXCEPTION;
                break;
        }
        switch (method){
            case "GET":
                customInfo.put("subject",status.value()+" "+status.getReasonPhrase().toUpperCase());
                Boolean useDetailedMode = globalServiceConfig.getUseDetailedMode();
                ModelAndView modelAndView = new ModelAndView(viewToolkit.toView("error/Error"));
                modelAndView.addObject("rid",request.getAttribute(StandardFields.FILTER_REQUEST_ID));
                modelAndView.addObject("code",resultCode.getCode());
                modelAndView.addObject("httpCode",status.value());
                modelAndView.addObject("title",resultCode.getInfo());
                modelAndView.addObject("date", Time.getCurrentTime());
                modelAndView.addObject("appName", applicationName);
                modelAndView.addObject("version", Version.$DEFAULT.getVersion());
                if (useDetailedMode){
                    modelAndView.addObject("extra",resultCode.getExtraInfo());
                    modelAndView.addObject("innerCode",resultCode.getInnerCode());
                }
                Object extraMessage = request.getAttribute("extraMessage");
                if (Validator.strNotBlank(extraMessage)){
                    modelAndView.addObject("extraMessage",extraMessage);
                }
                modelAndView.addAllObjects(customInfo);
                return modelAndView;
            case "POST":
            default:
                break;
        }
        return null;
    }

}