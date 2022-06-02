package cn.toutatis.support.spring.beans

import cn.toutatis.support.spring.VoidContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class AutoInsertDatabaseComponent {

    /*TODO database.username*/
//    @Value("\${}")
    private lateinit var username:String

    fun checkAndInsert(){
        for (needCreatedBean in VoidContext.needCreatedBeans) {

        }
    }

    fun pushField(){

    }

}