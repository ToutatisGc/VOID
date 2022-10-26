package cn.toutatis.xvoid.spring.core.security;

import cn.toutatis.core.root.security.handler.LogOutHandler;
import cn.toutatis.xvoid.spring.PkgInfo;
import cn.toutatis.xvoid.spring.core.security.handler.SecurityHandler;
import cn.toutatis.xvoid.support.spring.config.RunMode;
import cn.toutatis.xvoid.support.spring.config.VoidConfiguration;
import cn.toutatis.xvoid.toolkit.file.FileToolkit;
import cn.toutatis.xvoid.toolkit.formatting.EnumToolkit;
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

    private final VoidConfiguration voidConfiguration;

    public Security(VoidSecurityAuthenticationService voidAuthenticationService, SecurityHandler securityHandler, LogOutHandler logOutHandler, VoidConfiguration voidConfiguration) {
        this.voidAuthenticationService = voidAuthenticationService;
        this.securityHandler = securityHandler;
        this.logOutHandler = logOutHandler;
        this.voidConfiguration = voidConfiguration;
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


    private String[] getOpenMapping(){
        RunMode mode = voidConfiguration.getMode();
        logger.info("[{}]当前启动模式为:{}",PkgInfo.MODULE_NAME,mode);
        String[] openMappings = new String[1];
        if (mode == RunMode.DEBUG){
            logger.warn("[{}]注意开发模式为:DEBUG[调试模式],将忽略所有权限控制.",PkgInfo.MODULE_NAME);
            openMappings[0] = "/**";
            return openMappings;
        }
        Properties properties = new Properties();
        File file = null;
        try {
            file = new File(Objects.requireNonNull(
                    FileToolkit.getResourcesFile("openMapping.properties")).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (file != null && file.exists() && file.isFile()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                properties.load(fileInputStream);
                List<String> opens = new ArrayList<>(20);
                Set<String> propertyNames = properties.stringPropertyNames();
                Iterator<String> iterator = propertyNames.iterator();
                String isOpenField = "OPEN";
                while (iterator.hasNext()){
                    String next = iterator.next();
                    String rightStr = properties.getProperty(next);
                    RunMode pathRight = EnumToolkit.getValue(RunMode.class, rightStr);
                    if (pathRight != null){
                        if (pathRight == RunMode.DEV){
                            opens.add(next);
                            logger.info("["+ PkgInfo.MODULE_NAME+"] 添加DEV开发路径权限："+next);
                        }else {
                            logger.warn("["+ PkgInfo.MODULE_NAME+"] 未知权限路径[{}]和权限[{}]",next,rightStr);
                        }
                    }else{
                        if (isOpenField.equalsIgnoreCase(rightStr)) {
                            opens.add(next);
                            logger.info("[" + PkgInfo.MODULE_NAME + "] 添加OPEN开放路径权限：" + next);
                            continue;
                        }
                        logger.warn("["+ PkgInfo.MODULE_NAME+"] 未知权限路径："+next);
                    }
                }
                openMappings = opens.toArray(openMappings);
            } catch (IOException e) {
                logger.error("openMapping.properties not found , replace empty String[]");
                openMappings = new String[0];
            }
        }else{
            logger.error("openMapping.properties not found , replace empty String[]");
        }
        return openMappings;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
