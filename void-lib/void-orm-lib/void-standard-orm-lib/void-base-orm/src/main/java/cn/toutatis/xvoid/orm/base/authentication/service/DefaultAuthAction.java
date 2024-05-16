package cn.toutatis.xvoid.orm.base.authentication.service;

import cn.toutatis.xvoid.common.result.ResultCode;
import cn.toutatis.xvoid.common.standard.StandardFields;
import cn.toutatis.xvoid.common.enums.MessageType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 认证过程中一些默认行为
 * @author Toutatis_Gc
 */
public interface DefaultAuthAction {

    default UsernameNotFoundException throwInfo(HttpServletRequest request,MessageType type, Object message){
        throw throwInfo(request,type,message,true);
    }

    /**
     * 认证错误时抛出异常交由Security异常处理处理返回信息
     * @param type 返回消息类型
     * @param message 消息内容
     * @param normal 是否为正操情况
     * @return 异常 交由上层处理
     */
    default UsernameNotFoundException throwInfo(HttpServletRequest request, MessageType type, Object message, Boolean normal){
        request.setAttribute(StandardFields.VOID_HTTP_ATTRIBUTE_MESSAGE_KEY,message);
        if (normal){
            request.setAttribute(StandardFields.VOID_HTTP_ATTRIBUTE_STATUS_KEY, ResultCode.AUTHENTICATION_FAILED);
        }else{
            request.setAttribute(StandardFields.VOID_HTTP_ATTRIBUTE_STATUS_KEY, ResultCode.ILLEGAL_OPERATION);
        }
        throw new UsernameNotFoundException(type.name());
    }
}
