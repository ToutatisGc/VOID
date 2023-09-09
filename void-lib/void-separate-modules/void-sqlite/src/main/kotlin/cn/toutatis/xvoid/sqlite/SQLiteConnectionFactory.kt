package cn.toutatis.xvoid.sqlite

import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.errorWithModule
import org.sqlite.SQLiteConnection
import java.io.File
import java.io.FileNotFoundException
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
            try {
                val sqLiteConnection = DriverManager.getConnection("jdbc:sqlite:${db.path}") as SQLiteConnection
                val t = Thread {
                    System.err.println(666)
                }
                Runtime.getRuntime().addShutdownHook(t);
                return sqLiteConnection;
            }catch (e:Exception){
                val log = logger.errorWithModule(Meta.MODULE_NAME, "SQLite数据库[${db.name}]连接异常")
                e.printStackTrace()
                throw RuntimeException(log)
            }
        }else{
            val log = logger.errorWithModule(Meta.MODULE_NAME, "SQLite数据库[${db.name}]不存在")
            throw FileNotFoundException(log)
        }
    }

}