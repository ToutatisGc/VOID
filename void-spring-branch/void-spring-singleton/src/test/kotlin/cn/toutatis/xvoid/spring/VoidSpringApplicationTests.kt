package cn.toutatis.xvoid.spring

import cn.toutatis.xvoid.spring.business.user.persistence.SystemUserLoginRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class VoidSpringApplicationTests {

    @Autowired
    private lateinit var systemUserLoginRepository: SystemUserLoginRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val bCryptPasswordEncoder = BCryptPasswordEncoder()

    @Test
    fun contextLoads() {
        System.err.println(mockMvc)
        val encode = bCryptPasswordEncoder.encode("123456")
        System.err.println(encode.toString())
    }


}
