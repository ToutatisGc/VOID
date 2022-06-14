package cn.toutatis.spring.core.config.orm.mybatisplus

import cn.toutatis.data.common.DataStatus
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * @author Toutatis_Gc
 * 元数据填充
 */
@Component
class MybatisPlusMetaObjectHandler : MetaObjectHandler {

    override fun insertFill(metaObject: MetaObject) {
        val now = LocalDateTime.now()
        this.strictInsertFill(metaObject, "createTime", { now }, LocalDateTime::class.java)
        this.strictInsertFill(metaObject, "lastUpdateTime", { now }, LocalDateTime::class.java)
        this.fillStrategy(metaObject,"version",0)
        this.fillStrategy(metaObject,"status",DataStatus.SYS_OPEN_0000)
        this.fillStrategy(metaObject,"logicDeleted",DataStatus.SYS_OPEN_0000)
    }

    override fun updateFill(metaObject: MetaObject) {
        this.strictUpdateFill(metaObject, "lastUpdateTime", { LocalDateTime.now() }, LocalDateTime::class.java)
    }
}