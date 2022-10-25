package cn.toutatis.xvoid.spring.core.security;

import cn.toutatis.core.root.security.handler.LogOutHandler;
import cn.toutatis.xvoid.spring.PkgInfo;
import cn.toutatis.xvoid.spring.core.security.handler.SecurityHandler;
import cn.toutatis.xvoid.toolkit.file.FileToolkit;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

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

    public Security(VoidSecurityAuthenticationService voidAuthenticationService, SecurityHandler securityHandler, LogOutHandler logOutHandler) {
        this.voidAuthenticationService = voidAuthenticationService;
        this.securityHandler = securityHandler;
        this.logOutHandler = logOutHandler;
    }

//    @Override
//    public void init(WebSecurity web) throws Exception {
////        web.securityInterceptor()
//        super.init(web);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        禁止csrfFilter
        http.csrf().disable();
        http.headers()
                .addHeaderWriter(VoidResponse.Companion::cors)
                .frameOptions().disable();
//        禁用Basic Auth
        http.httpBasic().disable();
//        表单认证
        http.formLogin()
                .loginPage("/auth/authPage")
                .loginProcessingUrl("/authentication")
                .usernameParameter("identity")
                .passwordParameter("secret")
                .successHandler(securityHandler)
                .failureHandler(securityHandler);
        /*开放路径*/
        http.authorizeRequests()
                .antMatchers(this.getOpenMapping())
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

//    @Autowired
//    WechatProperties wechatProperties;

    private String[] getOpenMapping(){
        Properties properties = new Properties();
        String[] openMappings = new String[0];
        File file = null;
        try {
            file = new File(Objects.requireNonNull(
                    FileToolkit.getResourcesFile("openMapping.properties")).toURI()
            );
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (file != null && file.exists() && file.isFile()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                properties.load(fileInputStream);
                List<String> opens = new ArrayList<>();
                Set<String> propertyNames = properties.stringPropertyNames();
                Iterator<String> iterator = propertyNames.iterator();
                String isOpenField = "OPEN";
                while (iterator.hasNext()){
                    String next = iterator.next();
                    String isOpen = properties.getProperty(next);
                    if (isOpenField.equalsIgnoreCase(isOpen)){
                        opens.add(next);
                        logger.info("["+ PkgInfo.MODULE_NAME+"] 添加开放路径："+next);
                    }
                }
                openMappings = opens.toArray(openMappings);
            } catch (IOException e) {
                System.out.println("openMapping.properties not found , replace empty String[]");
                openMappings = new String[0];
            }
        }
        return openMappings;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
