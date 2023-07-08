package cn.toutatis.xvoid.spring.core.security.config;

import cn.toutatis.core.root.security.handler.LogOutHandler;
import cn.toutatis.xvoid.spring.core.security.VoidResponse;
import cn.toutatis.xvoid.spring.core.security.access.VoidSecurityAuthenticationService;
import cn.toutatis.xvoid.spring.core.security.config.handler.SecurityHandler;
import cn.toutatis.xvoid.support.spring.core.aop.filters.AnyPerRequestInjectRidFilter;
import cn.toutatis.xvoid.support.spring.enhance.mapping.XvoidMappingResolver;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;

/**
 * @author Toutatis_Gc
 * spring boot security配置
 */
@Configuration
@EnableWebSecurity
public class Security {

    private final Logger logger = LoggerToolkit.getLogger(Security.class);

    private final VoidSecurityAuthenticationService voidAuthenticationService;

    private final AuthenticationManagerBuilder authenticationManager;

    private final SecurityHandler securityHandler;

    private final LogOutHandler logOutHandler;

    private final XvoidMappingResolver xvoidMappingResolver;

    private final AnyPerRequestInjectRidFilter anyRequestFilter;

    @Autowired
    private CustomAuthenticationManager customAuthenticationManager;

    public Security(VoidSecurityAuthenticationService voidAuthenticationService, SecurityHandler securityHandler, LogOutHandler logOutHandler, XvoidMappingResolver xvoidMappingResolver, AnyPerRequestInjectRidFilter anyRequestFilter, AuthenticationManagerBuilder authenticationManager, DaoAuthenticationProvider daoAuthenticationProvider) {
        this.voidAuthenticationService = voidAuthenticationService;
        this.securityHandler = securityHandler;
        this.logOutHandler = logOutHandler;
        this.xvoidMappingResolver = xvoidMappingResolver;
        this.anyRequestFilter = anyRequestFilter;
        this.authenticationManager = authenticationManager;
        this.authenticationManager.authenticationProvider(customAuthenticationManager.getDaoAuthenticationProvider());
    }


    public static final String AUTH_PATH = "/auth/authentication";

    @Bean
    public AuthorizationManager authenticationManager(){
        return (AuthorizationManager<RequestAuthorizationContext>) (authenticationSupplier, requestAuthorizationContext) -> {
            // 当前用户的权限信息 比如角色
            Collection<? extends GrantedAuthority> authorities = authenticationSupplier.get().getAuthorities();
            // 当前请求上下文
            // 我们可以获取携带的参数
            Map<String, String> variables = requestAuthorizationContext.getVariables();
            // 我们可以获取原始request对象
            HttpServletRequest request = requestAuthorizationContext.getRequest();
            //todo 根据这些信息 和业务写逻辑即可 最终决定是否授权 isGranted
            boolean isGranted = true;
            return new AuthorizationDecision(isGranted);
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf((AbstractHttpConfigurer::disable))
                .httpBasic(AbstractHttpConfigurer::disable);

        httpSecurity.addFilterBefore(anyRequestFilter, BasicAuthenticationFilter.class);
        httpSecurity.addFilterAt(usernamePasswordAuthenticationJsonFilter(), UsernamePasswordAuthenticationFilter.class);

        httpSecurity.headers(securityHeadersConfigurer -> {
            securityHeadersConfigurer
                    .addHeaderWriter(VoidResponse.Companion::cors)
                    .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
            ;
        });

        httpSecurity.formLogin((customizer) ->{
            customizer
                    .loginPage("/auth/login/page")
                    .loginProcessingUrl(AUTH_PATH)
                    .usernameParameter("identity")
                    .passwordParameter("secret")
                    .successHandler(securityHandler)
                    .failureHandler(securityHandler);
        });

        httpSecurity.authorizeHttpRequests(requestMatcherRegistry -> {
            requestMatcherRegistry.requestMatchers(xvoidMappingResolver.getSecurityPermittedPaths()).permitAll();
            requestMatcherRegistry
                    .anyRequest()
                    .access(authenticationManager());
        });

        httpSecurity.exceptionHandling(exceptionHandlingConfigurer ->{
            exceptionHandlingConfigurer
                    .authenticationEntryPoint(securityHandler)
                    .accessDeniedHandler(securityHandler);
        });

        httpSecurity.logout(logoutConfigurer ->{
            logoutConfigurer.logoutSuccessHandler(logOutHandler);
        });

//        /*基于path的认证*/
//        httpSecurity.authorizeRequests()
//                .anyRequest()
//                .access("@AntUrlService.hasPermission(request,authentication)");


        return httpSecurity.build();
    }




    @Bean
    public UsernamePasswordAuthenticationJsonFilter usernamePasswordAuthenticationJsonFilter(){
        UsernamePasswordAuthenticationJsonFilter jsonInterceptor = new UsernamePasswordAuthenticationJsonFilter();
        try {
            jsonInterceptor.setAuthenticationManager(customAuthenticationManager);
            jsonInterceptor.setFilterProcessesUrl(AUTH_PATH);
            jsonInterceptor.setUsernameParameter("identity");
            jsonInterceptor.setPasswordParameter("secret");
            jsonInterceptor.setAuthenticationSuccessHandler(securityHandler);
            jsonInterceptor.setAuthenticationFailureHandler(securityHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonInterceptor;
    }
}
