package cn.toutatis.xvoid.spring

import cn.toutatis.xvoid.spring.core.security.access.persistence.SystemUserLoginRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootTest
class VoidSpringApplicationTests {

    @Autowired
    private lateinit var systemUserLoginRepository: SystemUserLoginRepository

    private val bCryptPasswordEncoder = BCryptPasswordEncoder()

    @Test
    fun contextLoads() {
        val encode = bCryptPasswordEncoder.encode("123456")
        System.err.println(encode.toString())
    }


}
