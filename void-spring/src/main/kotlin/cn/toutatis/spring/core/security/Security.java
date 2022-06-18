package cn.toutatis.spring.core.security;

import cn.toutatis.core.root.security.handler.LogOutHandler;

import cn.toutatis.spring.core.security.handler.VoidSecurityHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author Toutatis_Gc
 * spring boot security配置
 */
@Configuration
public class Security extends WebSecurityConfigurerAdapter {

    @Value("${void.dev-mode:true}")
    private boolean devMode;

//    @Autowired
//    SecurityAuthenticationService authenticationService;

    @Autowired
    VoidSecurityHandler securityHandler;

    @Autowired
    LogOutHandler logOutHandler;

//    @Override
//    public void init(WebSecurity web) throws Exception {
////        web.securityInterceptor()
//        super.init(web);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        禁止csrfFilter
        http.csrf().disable();
//
//        http.headers()
//                .addHeaderWriter(VoidResponse.Companion::cors)
//                .frameOptions().disable();
//        禁用弹框
        http.httpBasic().disable();

//        http.headers().a
//        表单认证
        http.formLogin()
                .loginPage("/auth/authPage")
                .loginProcessingUrl("/authentication")
                .usernameParameter("identity")
                .passwordParameter("secret")
                .successHandler(securityHandler)
                .failureHandler(securityHandler);
        http.authorizeRequests().antMatchers(this.getOpenMapping()).permitAll()
                .and()
                .authorizeRequests().anyRequest()
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
//        daoAuthenticationProvider.setUserDetailsService(authenticationService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

//    @Autowired
//    WechatProperties wechatProperties;

    private String[] getOpenMapping(){
        Properties properties = new Properties();
        String[] openMappings = new String[0];
        String path = Objects.requireNonNull(Objects.requireNonNull(ClassUtils.getDefaultClassLoader()).getResource("")).getPath();
        File file = new File(path+"openMapping.properties");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            properties.load(fileInputStream);
            List<String> opens = new ArrayList<>();
//            String checkTextPath = wechatProperties.getCheckTextPath();
//            if (!"".equals(checkTextPath)){
//                opens.add(checkTextPath);
//            }
            Set<String> propertyNames = properties.stringPropertyNames();
            Iterator<String> iterator = propertyNames.iterator();
            String isOpenField = "OPEN";
            while (iterator.hasNext()){
                String next = iterator.next();
                String isOpen = properties.getProperty(next);
                if (isOpenField.equalsIgnoreCase(isOpen)){
                    opens.add(next);
                }
            }
            openMappings = opens.toArray(openMappings);
        } catch (IOException e) {
            System.out.println("openMapping.properties not found , replace empty String[]");
            openMappings = new String[0];
        }
        return openMappings;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
