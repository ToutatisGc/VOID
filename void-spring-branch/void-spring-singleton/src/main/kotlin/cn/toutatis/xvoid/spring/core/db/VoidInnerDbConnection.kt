package cn.toutatis.xvoid.spring.core.db

import cn.toutatis.xvoid.sqlite.Meta
import cn.toutatis.xvoid.sqlite.SQLiteConnectionFactory
import cn.toutatis.xvoid.sqlite.SQLiteShell
import cn.toutatis.xvoid.toolkit.file.FileToolkit
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.errorWithModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.sqlite.SQLiteConnection
import java.io.FileNotFoundException
import java.net.URL
import java.nio.file.Paths

@Configuration
class VoidInnerDbConnection {

    private val logger = LoggerToolkit.getLogger(this.javaClass)

    @Bean
    fun sqliteShell(): SQLiteShell {
        val sqLiteConnectionFactory = SQLiteConnectionFactory()
        val resourcesFile: URL? = FileToolkit.getResourcesFile("sql/VOID.db")
        if (resourcesFile != null) {
            val dbFile = Paths.get(resourcesFile.toURI()).toFile()
            val createConnection: SQLiteConnection = sqLiteConnectionFactory.createConnection(dbFile)
            return SQLiteShell(createConnection)
        }else{
            val errorWithModule = logger.errorWithModule(Meta.MODULE_NAME, "SPRING", "缺失VOID数据库文件")
            throw FileNotFoundException(errorWithModule)
        }
    }

}