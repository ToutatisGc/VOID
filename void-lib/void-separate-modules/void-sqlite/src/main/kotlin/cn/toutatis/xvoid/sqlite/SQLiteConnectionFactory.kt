package cn.toutatis.xvoid.sqlite

import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.errorWithModule
import org.sqlite.SQLiteConnection
import java.io.File
import java.lang.IllegalArgumentException
import java.sql.DriverManager

class SQLiteConnectionFactory {

    private val logger = LoggerToolkit.getLogger(javaClass)

    companion object{

        /**
         * 驱动名称
         */
        const val DRIVER_CLASS_NAME = "org.sqlite.JDBC"

        /**
         * 初始化加载驱动
         */
        init {
            Class.forName(DRIVER_CLASS_NAME)
        }

    }


    fun createConnection(db: File): SQLiteConnection {
        if (db.exists() && db.isFile ) {
            return DriverManager.getConnection("jdbc:sqlite:${db.path}") as SQLiteConnection;
        }else{
            val log = logger.errorWithModule(VoidModuleInfo.MODULE_NAME, "数据库[${db.name}]不存在")
            throw IllegalArgumentException(log)
        }
    }

}