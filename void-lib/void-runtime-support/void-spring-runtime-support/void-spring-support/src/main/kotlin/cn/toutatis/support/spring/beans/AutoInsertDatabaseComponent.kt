package cn.toutatis.support.spring.beans

import cn.hutool.core.util.ReflectUtil
import cn.toutatis.common.standard.StandardComponentPool
import cn.toutatis.support.spring.VoidContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component(StandardComponentPool.VOID_AUTO_INSERT_OBJS_COMPONENT)
class AutoInsertDatabaseComponent {

    @Autowired
    private lateinit var datasource: DataSource

    fun checkAndInsert(){
        for (needCreatedBean in VoidContext.needCreatedBeans) {
//            needCreatedBean.getAnnotation()
        }
    }

    fun pushField(){
//        ReflectUtil.getFields()
    }

}