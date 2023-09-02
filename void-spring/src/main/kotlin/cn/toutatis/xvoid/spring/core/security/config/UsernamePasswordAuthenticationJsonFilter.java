package cn.toutatis.xvoid.spring.core.security.config;

import cn.toutatis.xvoid.common.standard.AuthFields;
import cn.toutatis.xvoid.common.standard.HttpHeaders;
import cn.toutatis.xvoid.spring.support.core.aop.filters.AnyPerRequestInjectRidFilter;
import cn.toutatis.xvoid.spring.support.core.aop.interceptor.RequestLogInterceptor;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Touitatis_Gc
 * 原本的用户登录认证是form表单格式，为了更好的兼容性，改为application/json格式
 * 而且增加了对框架功能的集成
 */
public class UsernamePasswordAuthenticationJsonFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    private RequestLogInterceptor requestlogInterceptor;

    @Autowired
    private AnyPerRequestInjectRidFilter anyPerRequestInjectRidFilter;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        anyPerRequestInjectRidFilter.fillRequestId(request, response);
        JSONObject recordInfo = new JSONObject(2);
        recordInfo.put("className",this.getClass().toString());
        recordInfo.put("methodName","attemptAuthentication");
        requestlogInterceptor.logRequest(request,response,recordInfo);
        if(!HttpHeaders.POST.equals(request.getMethod())){
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        if(Validator.strIsBlank(request.getContentType())){
            throw new NullPointerException("Content-Type is required.");
        }else{
            String contentType = request.getContentType();
            // 现在只允许application/json格式
            if (!MediaType.APPLICATION_JSON_VALUE.equals(contentType)) {
                throw new AuthenticationServiceException("Content-Type not supported: " + contentType);
            }
        }
        if(request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)){
            Map<String,Object> userInfo;
            try {
                // 在此处注入属性sessionId
                userInfo = jacksonObjectMapper.readValue(request.getInputStream(), Map.class);
                Map<String,Object> o = (Map<String, Object>) userInfo.get(getUsernameParameter());
                o.put(AuthFields.XVOID_INTERNAL_ACTIVITY_AUTH_SESSION_KEY, request.getSession().getId());
                Object identity = JSON.toJSONString(o);
                Object secret = userInfo.get(getPasswordParameter());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(identity,secret);
                setDetails(request,authenticationToken);
                // 此处会调用JSessionId修改session
                return this.getAuthenticationManager().authenticate(authenticationToken);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.attemptAuthentication(request,response);
    }
}