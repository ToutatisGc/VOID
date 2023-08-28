package cn.toutatis.xvoid.spring.core.security.config;

import cn.toutatis.core.root.security.handler.LogOutHandler;
import cn.toutatis.xvoid.spring.core.security.access.VoidSecurityAuthenticationService;
import cn.toutatis.xvoid.spring.core.security.config.handler.SecurityHandler;
import cn.toutatis.xvoid.spring.support.core.aop.filters.AnyPerRequestInjectRidFilter;
import cn.toutatis.xvoid.spring.support.enhance.mapping.XvoidMappingResolver;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.GenericApplicationListenerAdapter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.context.DelegatingApplicationListener;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.*;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.util.Arrays;

/**
 * @author Toutatis_Gc
 * spring boot security配置
 */
@Configuration
public class Security extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerToolkit.getLogger(Security.class);

    private final VoidSecurityAuthenticationService voidAuthenticationService;

    private final SecurityHandler securityHandler;

    private final LogOutHandler logOutHandler;

    private final XvoidMappingResolver xvoidMappingResolver;

    private final AnyPerRequestInjectRidFilter anyRequestFilter;

    public static final String AUTH_PATH = "/auth/authentication";

    //sessesion失效所需要的监听器，spring security默认配置过个bean，我们只需要自动注入即可
    @Autowired
    DelegatingApplicationListener delegatingApplicationListener;

    public Security(VoidSecurityAuthenticationService voidAuthenticationService, SecurityHandler securityHandler, LogOutHandler logOutHandler, XvoidMappingResolver xvoidMappingResolver, AnyPerRequestInjectRidFilter anyRequestFilter) {
        this.voidAuthenticationService = voidAuthenticationService;
        this.securityHandler = securityHandler;
        this.logOutHandler = logOutHandler;
        this.xvoidMappingResolver = xvoidMappingResolver;
        this.anyRequestFilter = anyRequestFilter;
    }

//    @Override
//    public void init(WebSecurity web) throws Exception {
////        web.securityInterceptor()
//        super.init(web);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        System.err.println(1/0);
//        禁止csrfFilter
        http.csrf().disable();
        http.addFilterBefore(anyRequestFilter, BasicAuthenticationFilter.class);
        http.headers()
//                .addHeaderWriter(VoidResponse.Companion::cors)
                .frameOptions().disable();
//        禁用Basic Auth
        http.httpBasic().disable();
//        表单认证
        http.addFilterAt(usernamePasswordAuthenticationJsonFilter(), UsernamePasswordAuthenticationFilter.class);
        http.formLogin()
                .loginPage("/auth/login/page")
                .loginProcessingUrl(AUTH_PATH)
                .usernameParameter("identity")
                .passwordParameter("secret")
                .successHandler(securityHandler)
                .failureHandler(securityHandler);
        /*开放路径*/
        http.authorizeRequests()
                .antMatchers(xvoidMappingResolver.getSecurityPermittedPaths())
                .permitAll();
        /*基于path的认证*/
        http.authorizeRequests()
                .anyRequest()
                .access("@AntUrlService.hasPermission(request,authentication)");
        http.exceptionHandling()
                .authenticationEntryPoint(securityHandler)
                .accessDeniedHandler(securityHandler);
//      TODO 可能需要配置注销回调
        http.logout().logoutSuccessHandler(logOutHandler);
    }

//    @Bean
//    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
//        SessionRegistryImpl sessionRegistry = new SessionRegistryImpl();
//        ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlStrategy =
//                new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry);
//        delegatingApplicationListener.addListener(new GenericApplicationListenerAdapter(sessionRegistry));
//        concurrentSessionControlStrategy.setMaximumSessions(1);
//        concurrentSessionControlStrategy.setExceptionIfMaximumExceeded(true);
//        CompositeSessionAuthenticationStrategy delegateStrategies = new CompositeSessionAuthenticationStrategy(
//                Arrays.asList(concurrentSessionControlStrategy,
//                        new ChangeSessionIdAuthenticationStrategy(),
//                        new RegisterSessionAuthenticationStrategy(sessionRegistry)
//                ));
//        return delegateStrategies;
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setUserDetailsService(voidAuthenticationService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public UsernamePasswordAuthenticationJsonFilter usernamePasswordAuthenticationJsonFilter(){
        UsernamePasswordAuthenticationJsonFilter jsonInterceptor = new UsernamePasswordAuthenticationJsonFilter();
        try {
            jsonInterceptor.setAuthenticationManager(authenticationManager());
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
