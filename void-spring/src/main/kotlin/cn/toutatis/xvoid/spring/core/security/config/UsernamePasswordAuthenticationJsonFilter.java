package cn.toutatis.xvoid.spring.core.security.config;

import cn.toutatis.xvoid.support.spring.core.aop.filters.AnyPerRequestInjectRidFilter;
import cn.toutatis.xvoid.support.spring.core.aop.interceptor.RequestLogInterceptor;
import cn.toutatis.xvoid.toolkit.validator.Validator;
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
        if(!"POST".equals(request.getMethod())){
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        if(Validator.strIsBlank(request.getContentType())){

        }
        if(request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)){
            Map<String,Object> userInfo;
            try {
                userInfo = jacksonObjectMapper.readValue(request.getInputStream(), Map.class);
                Object username = userInfo.get(getUsernameParameter());
                Object password = userInfo.get(getPasswordParameter());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
                setDetails(request,authenticationToken);
                return this.getAuthenticationManager().authenticate(authenticationToken);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.attemptAuthentication(request,response);
    }
}