package cn.toutatis.xvoid.spring.core.security.access.auth;

import cn.toutatis.xvoid.common.result.DataStatus;
import cn.toutatis.xvoid.orm.base.authentication.entity.derive.FormUserDetails;
import cn.toutatis.xvoid.orm.base.authentication.entity.RequestAuthEntity;
import cn.toutatis.xvoid.orm.base.authentication.entity.SystemUserLogin;
import cn.toutatis.xvoid.orm.base.authentication.enums.MessageType;
import cn.toutatis.xvoid.orm.base.authentication.service.DefaultAuthAction;
import cn.toutatis.xvoid.orm.base.authentication.service.SystemUserLoginService;
import cn.toutatis.xvoid.common.standard.AuthValidationMessage;
import cn.toutatis.xvoid.toolkit.clazz.LambdaToolkit;
import cn.toutatis.xvoid.toolkit.constant.Time;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author Toutatis_Gc
 * @date 2022/11/23 11:07
 */
@Service
public class FormUserAuthService implements DefaultAuthAction {

    private final SystemUserLoginService systemUserLoginService;

    private final HttpServletRequest request;

    public FormUserAuthService(SystemUserLoginService systemUserLoginService, HttpServletRequest request) {
        this.systemUserLoginService = systemUserLoginService;
        this.request = request;
    }

    public UserDetails findSimpleUser(RequestAuthEntity authEntity) throws Exception{
        QueryWrapper<SystemUserLogin> queryWrapper = Wrappers.query();
        String account = authEntity.getAccount();
        String accountField = LambdaToolkit.getFieldName(SystemUserLogin::getAccount);
        queryWrapper
                .eq(accountField,account).or()
                .eq(LambdaToolkit.getFieldName(SystemUserLogin::getEmail), account).or()
                .eq(LambdaToolkit.getFieldName(SystemUserLogin::getPhoneCode), account).or()
        ;
        SystemUserLogin user = systemUserLoginService.getOneObj(queryWrapper);
        if (user!=null){
            FormUserDetails formUserDetails = new FormUserDetails();
            DataStatus status = user.getStatus();
            if (status == DataStatus.SYS_OPEN_0000){
                LocalDateTime expiredTime = user.getExpiredTime();
                if (expiredTime != null){
                    formUserDetails.setAccountNonExpired(Time.isBeforeNow(expiredTime));
                }else {
                    formUserDetails.setAccountNonExpired(true);
                }
                JSONObject userInfo = new JSONObject();
                userInfo.put(LambdaToolkit.getFieldName(SystemUserLogin::getId),user.getId());
                userInfo.put(accountField,user.getAccount());
                userInfo.put("username",user.getUsername());
                userInfo.put("email",user.getEmail());
                userInfo.put("phoneCode",user.getPhoneCode());
                formUserDetails.setSecret(user.getSecret());
                formUserDetails.setAccount(user.getAccount());
                formUserDetails.setUserMetaInfo(userInfo);
            }else{
                switch (status){
                    case SYS_LOCKED_0000,SYS_LOCKED_0001 -> {
                        formUserDetails.setAccountNonLocked(false);
                    }
                    default -> {
                        formUserDetails.setEnabled(false);
                    }
                }
            }
            return formUserDetails;
        }else{
            throw throwInfo(request,MessageType.STRING, AuthValidationMessage.ACCOUNT_NOT_EXIST);
        }
    }

}
