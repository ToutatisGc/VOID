package cn.toutatis.support.spring.enhance

import cn.toutatis.common.standard.StandardComponentPool
import cn.toutatis.support.spring.VoidContext
import org.springframework.stereotype.Component

@Component(StandardComponentPool.VOID_AUTO_INSERT_OBJS_COMPONENT)
class AutoInsertDatabaseComponent {

//    @Autowired
//    private lateinit var datasource: DataSource

    fun checkAndInsert(){
        for (needCreatedBean in VoidContext.needCreatedBeans) {
//            needCreatedBean.getAnnotation()
        }
    }

    fun pushField(){
//        ReflectUtil.getFields()
    }

}