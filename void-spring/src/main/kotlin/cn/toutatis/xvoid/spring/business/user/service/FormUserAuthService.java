package cn.toutatis.xvoid.spring.business.user.service;

import cn.toutatis.xvoid.common.standard.StandardFields;
import cn.toutatis.xvoid.common.result.DataStatus;
import cn.toutatis.xvoid.common.result.ResultCode;
import cn.toutatis.xvoid.orm.base.authentication.entity.SystemUserLogin;
import cn.toutatis.xvoid.spring.core.security.access.ValidationMessage;
import cn.toutatis.xvoid.spring.core.security.access.VoidSecurityAuthenticationService;
import cn.toutatis.xvoid.spring.business.user.entity.FormUserDetails;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author Toutatis_Gc
 * @date 2022/11/23 11:07
 */
@Service
public class FormUserAuthService {

    private final SystemUserLoginService systemUserLoginService;

    public FormUserAuthService(SystemUserLoginService systemUserLoginService) {
        this.systemUserLoginService = systemUserLoginService;
    }

    @Autowired
    private HttpServletRequest request;

    public UserDetails findSimpleUser(JSONObject identity) {
        QueryWrapper<SystemUserLogin> queryWrapper = new QueryWrapper<>();
        String username = identity.getString("username");

        queryWrapper
                .eq("email",username).or()
                .eq("account", username).or()
                .eq("phoneCode", username).or()
        ;
        SystemUserLogin user = systemUserLoginService.getOneObj(queryWrapper);
        if (user!=null){
            FormUserDetails formUserDetails = new FormUserDetails();
            DataStatus status = user.getStatus();
            if (status == DataStatus.SYS_OPEN_0000){
                LocalDateTime expiredTime = user.getExpiredTime();
                if (expiredTime != null){
                    formUserDetails.setAccountNonExpired(LocalDateTime.now().isBefore(expiredTime));
                }else {
                    formUserDetails.setAccountNonExpired(true);
                }
                JSONObject userInfo = new JSONObject();
                userInfo.put("id",user.getId());
                userInfo.put("account",user.getAccount());
                userInfo.put("username",user.getUsername());
                userInfo.put("email",user.getEmail());
                userInfo.put("phoneCode",user.getPhoneCode());
                formUserDetails.setSecret(user.getSecret());
                formUserDetails.setUserInfo(userInfo);
                formUserDetails.setEnabled(true);
                /*TODO其余异常*/
                formUserDetails.setAccountNonLocked(true);
            }else{
                formUserDetails.setEnabled(false);
            }
            return formUserDetails;
        }else{
            throw throwInfo(VoidSecurityAuthenticationService.MessageType.STRING,ValidationMessage.USER_NOT_EXIST);
        }
    }

    private UsernameNotFoundException throwInfo(VoidSecurityAuthenticationService.MessageType type, Object message){
        throw throwInfo(type,message,true);
    }

    /**
     * @param type 返回消息类型
     * @param message 消息内容
     * @param normal 是否为正操情况
     * @return 异常 交由上层处理
     */
    private UsernameNotFoundException throwInfo(VoidSecurityAuthenticationService.MessageType type, Object message,Boolean normal){
        request.setAttribute(StandardFields.VOID_HTTP_ATTRIBUTE_MESSAGE_KEY,message);
        if (normal){
            request.setAttribute(StandardFields.VOID_HTTP_ATTRIBUTE_STATUS_KEY, ResultCode.AUTHENTICATION_FAILED);
        }else{
            request.setAttribute(StandardFields.VOID_HTTP_ATTRIBUTE_STATUS_KEY, ResultCode.ILLEGAL_OPERATION);
        }
        throw new UsernameNotFoundException(type.name());
    }

}
