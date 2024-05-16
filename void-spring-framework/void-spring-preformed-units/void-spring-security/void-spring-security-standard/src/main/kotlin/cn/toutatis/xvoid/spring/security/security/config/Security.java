package cn.toutatis.xvoid.spring.security.security.config;

import cn.toutatis.xvoid.spring.security.security.config.handler.LogOutHandler;
import cn.toutatis.xvoid.common.standard.AuthFields;
import cn.toutatis.xvoid.spring.security.security.access.VoidSecurityAuthenticationService;
import cn.toutatis.xvoid.spring.security.security.config.handler.SecurityHandler;
import cn.toutatis.xvoid.spring.support.core.aop.filters.AnyPerRequestInjectRidFilter;
import cn.toutatis.xvoid.spring.support.enhance.mapping.XvoidMappingResolver;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

/**
 * @author Toutatis_Gc
 * spring boot security配置
 */
@Configuration
public class Security<S extends Session> extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerToolkit.getLogger(Security.class);

    private final VoidSecurityAuthenticationService voidAuthenticationService;

    private final SecurityHandler securityHandler;

    private final LogOutHandler logOutHandler;

    private final XvoidMappingResolver xvoidMappingResolver;

    private final AnyPerRequestInjectRidFilter anyRequestFilter;

    public static final String AUTH_PATH = "/auth/authentication";

    @Autowired
    private FindByIndexNameSessionRepository<S> sessionRepository;

    public Security(VoidSecurityAuthenticationService voidAuthenticationService, SecurityHandler securityHandler, LogOutHandler logOutHandler, XvoidMappingResolver xvoidMappingResolver, AnyPerRequestInjectRidFilter anyRequestFilter) {
        this.voidAuthenticationService = voidAuthenticationService;
        this.securityHandler = securityHandler;
        this.logOutHandler = logOutHandler;
        this.xvoidMappingResolver = xvoidMappingResolver;
        this.anyRequestFilter = anyRequestFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
//        禁止csrfFilter
        http.csrf().disable();
        http.addFilterBefore(anyRequestFilter, BasicAuthenticationFilter.class);
        http.rememberMe((rememberMe) -> rememberMe.rememberMeServices(rememberMeServices()));
//        http.sessionManagement((sessionManagement) ->
//                        sessionManagement
//                                .maximumSessions(2)
//                                .sessionRegistry(sessionRegistry())
//        );
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

    @Bean
    public SpringSessionRememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices =
                new SpringSessionRememberMeServices();
        // TODO 记住session操作
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }

    @Bean
    public SpringSessionBackedSessionRegistry<S> sessionRegistry() {
        return new SpringSessionBackedSessionRegistry<>(this.sessionRepository);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
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
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
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
            jsonInterceptor.setUsernameParameter(AuthFields.IDENTITY);
            jsonInterceptor.setPasswordParameter(AuthFields.SECRET);
            jsonInterceptor.setAuthenticationSuccessHandler(securityHandler);
            jsonInterceptor.setAuthenticationFailureHandler(securityHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonInterceptor;
    }
}
