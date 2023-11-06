package cn.toutatis.xvoid.project.spring

import cn.toutatis.xvoid.common.annotations.database.AssignField
import cn.toutatis.xvoid.project.spring.VoidSpringContextVariables

class VoidSpringContextVariables {

    companion object {
        /**
         * AES加密密钥
         */
        const val AES_SECRET_KEY = "AES_SECRET"

        /**
         * 系统过期时间
         */
        const val SYSTEM_EXPIRED_KEY = "VOID_EXPIRED"

        /**
         * Db Already Exec Init
         * 数据库已初始化
         */
        const val DB_ALREADY_EXEC_INIT_KEY = "DB_ALREADY_EXEC_INIT"
    }

    @AssignField(name = AES_SECRET_KEY)
    var aesSecret: String? = null

    @AssignField(name = SYSTEM_EXPIRED_KEY)
    var systemExpired: String? = null

    @AssignField(name = DB_ALREADY_EXEC_INIT_KEY)
    var dbAlreadyInit: String? = null


}