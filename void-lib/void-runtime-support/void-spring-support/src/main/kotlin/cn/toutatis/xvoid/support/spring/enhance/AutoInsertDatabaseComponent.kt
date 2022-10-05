package cn.toutatis.xvoid.support.spring.enhance

import cn.toutatis.xvoid.common.standard.StandardComponentPool
import cn.toutatis.xvoid.support.VoidContext
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