package cn.toutatis.xvoid.spring.support.core.db.sqlite

import cn.toutatis.xvoid.common.standard.StandardComponentPool
import cn.toutatis.xvoid.sqlite.SQLiteConnectionFactory
import cn.toutatis.xvoid.sqlite.SQLiteShell
import cn.toutatis.xvoid.toolkit.file.FileToolkit
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.errorWithModule
import cn.toutatis.xvoid.toolkit.log.infoWithModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.sqlite.SQLiteConnection
import java.io.FileNotFoundException
import java.net.URL
import java.nio.file.Paths

@Configuration
class VoidInnerContextDbConnection {

    private val logger = LoggerToolkit.getLogger(this.javaClass)

    @Bean(StandardComponentPool.VOID_CONTEXT_SQLITE_DB_BEAN)
    fun sqliteShell(): SQLiteShell {
        val sqLiteConnectionFactory = SQLiteConnectionFactory()
        val resourcesFile: URL? = FileToolkit.getResourcesFile("sql/VOID.db")
        if (resourcesFile != null) {
            val dbFile = Paths.get(resourcesFile.toURI()).toFile()
            val createConnection: SQLiteConnection = sqLiteConnectionFactory.createConnection(dbFile)
            logger.infoWithModule(
                cn.toutatis.xvoid.sqlite.Meta.MODULE_NAME,
                cn.toutatis.xvoid.spring.support.Meta.SUB_MODULE_NAME,
                "已加载数据库[${dbFile.name}]"
            )
            return SQLiteShell(createConnection)
        }else{
            val errorWithModule = logger.errorWithModule(
                cn.toutatis.xvoid.sqlite.Meta.MODULE_NAME,
                cn.toutatis.xvoid.spring.support.Meta.SUB_MODULE_NAME,
                "缺失[VOID]数据库文件")
            throw FileNotFoundException(errorWithModule)
        }
    }


}