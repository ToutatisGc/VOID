package cn.toutatis.xvoid.spring.core.security.config;

import cn.toutatis.xvoid.spring.core.security.access.VoidSecurityAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private VoidSecurityAuthenticationService voidAuthenticationService;

    @Bean
    @Qualifier("daoAuthenticationProvider")
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setUserDetailsService(voidAuthenticationService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;

    public DaoAuthenticationProvider getDaoAuthenticationProvider() {
        return daoAuthenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return daoAuthenticationProvider.authenticate(authentication);
    }
}