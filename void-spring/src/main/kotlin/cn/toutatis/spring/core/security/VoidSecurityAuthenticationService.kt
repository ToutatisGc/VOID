package cn.toutatis.spring.core.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

class VoidSecurityAuthenticationService : UserDetailsService {


    override fun loadUserByUsername(username: String): UserDetails {
        TODO("Not yet implemented")
    }
}