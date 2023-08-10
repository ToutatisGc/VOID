package cn.toutatis.xvoid.spring.business.test;

import cn.toutatis.xvoid.common.standard.StandardFields;
import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog;
import cn.toutatis.xvoid.orm.base.infrastructure.services.SystemLogService;
import cn.toutatis.xvoid.spring.annotations.application.VoidController;
import cn.toutatis.xvoid.spring.configure.system.VoidConfiguration;
import cn.toutatis.xvoid.spring.support.enhance.controller.BaseControllerImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@VoidController
@RequestMapping("/test/log")
public class TestLogController extends BaseControllerImpl<SystemLog, SystemLogService> {

    @RequestMapping(value = "/rid",method = RequestMethod.POST)
    public void getRid(){
        Object attribute = request.getAttribute(StandardFields.FILTER_REQUEST_ID_KEY);
        System.err.println(attribute);
    }

}
