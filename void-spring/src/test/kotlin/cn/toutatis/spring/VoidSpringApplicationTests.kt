package cn.toutatis.spring

import cn.toutatis.data.common.security.SystemUserLogin
import cn.toutatis.spring.core.security.access.persistence.SystemUserLoginMapper
import cn.toutatis.spring.core.security.access.persistence.SystemUserLoginRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class VoidSpringApplicationTests {

    @Autowired
    private lateinit var systemUserLoginRepository: SystemUserLoginRepository

    @Autowired
    private lateinit var systemUserLoginMapper: SystemUserLoginMapper

    @Test
    fun contextLoads() {
        val userR = systemUserLoginRepository.findByAccount("admin")
        System.err.println(userR)

        val systemUserLogin = SystemUserLogin()
        systemUserLogin.account = "admin"
        systemUserLoginMapper.insert(systemUserLogin)
    }

}
