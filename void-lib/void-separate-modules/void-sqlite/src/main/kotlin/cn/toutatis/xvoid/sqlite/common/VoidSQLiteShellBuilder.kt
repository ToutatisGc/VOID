package cn.toutatis.xvoid.sqlite.common

import cn.toutatis.xvoid.sqlite.Meta
import cn.toutatis.xvoid.sqlite.SQLiteConnectionFactory
import cn.toutatis.xvoid.sqlite.SQLiteShell
import cn.toutatis.xvoid.toolkit.file.FileToolkit
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.errorWithModule
import cn.toutatis.xvoid.toolkit.log.infoWithModule
import org.sqlite.SQLiteConnection
import java.io.FileNotFoundException
import java.net.URL
import java.nio.file.Paths

/**
 * Void SQLite shell builder
 * 常用sqlite构建
 * @constructor Create empty Void s q lite shell builder
 */
object VoidSQLiteShellBuilder {

    private val logger = LoggerToolkit.getLogger(javaClass)

    private val factory = SQLiteConnectionFactory()

    /**
     * Build system context shell
     * 创建连接壳
     * @return 操作外壳
     */
    @JvmStatic
    fun buildSystemVoidContextShell(): SQLiteShell {
        return SQLiteShell(builderConnection("sql/VOID.db"))
    }

    /**
     * Builder connection
     * 创建SQLiteConnection
     * @param filename 数据库文件
     * @return SQLiteConnection连接对象
     */
    private fun builderConnection(filename:String): SQLiteConnection {
        val resourcesFile: URL? = FileToolkit.getResourcesFile(filename)
        if (resourcesFile != null) {
            val dbFile = Paths.get(resourcesFile.toURI()).toFile()
            val connection: SQLiteConnection = factory.createConnection(dbFile)
            logger.infoWithModule(Meta.MODULE_NAME, Meta.BUILDER_MODULE, "已加载数据库[${dbFile.name}]")
            return connection
        }else{
            val errorWithModule = logger.errorWithModule(Meta.MODULE_NAME, Meta.BUILDER_MODULE, "缺失[${filename}]数据库文件")
            throw FileNotFoundException(errorWithModule)
        }
    }

}