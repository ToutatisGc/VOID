package cn.toutatis.xvoid.spring.core.config

import cn.toutatis.xvoid.project.spring.VoidSpringContext
import cn.toutatis.xvoid.project.spring.VoidSpringProperties
import cn.toutatis.xvoid.sqlite.SQLiteShell
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class VoidPropertiesConfiguration {

    @Autowired
    private lateinit var sqLiteShell: SQLiteShell

    @PostConstruct
    fun init(){
        val properties = VoidSpringContext.properties
        properties.aesSecret = ""
    }

}