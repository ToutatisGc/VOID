package cn.toutatis.xvoid.sqlite

import cn.toutatis.xvoid.toolkit.file.FileToolkit
import org.junit.Test
import java.io.File

class SqliteTest {

    @Test
    fun `create SQLite connection`(): Unit {
        val sqLiteConnectionFactory = SQLiteConnectionFactory()
        val dbFile = FileToolkit.getResourcesFile("sqlite/db/sys/VOID.db")?.path.let { File(it!!) }
        val connection = sqLiteConnectionFactory.createConnection(dbFile)
        val statement = connection.createStatement()
    }

}