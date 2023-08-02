package cn.toutatis.xvoid.sqlite

import cn.toutatis.xvoid.toolkit.file.FileToolkit
import org.junit.Test
import java.io.File

class SqliteTest {

    @Test
    fun `create SQLite connection`(): Unit {
        val sqLiteConnectionFactory = SQLiteConnectionFactory()
        val dbFile = FileToolkit.getResourcesFile("sqlite/db/sys/VOID.db")?.path.let { File(it!!) }
        System.err.println(dbFile.path)
        val connection = sqLiteConnectionFactory.createConnection(dbFile).use { connection ->
            System.err.println(connection.isClosed)
            connection.autoCommit = true
            connection.createStatement().use { statement ->
                {
//                    val execute = statement.executeUpdate("""
//                      INSERT INTO config(key,value) values ('foo','bar')
//                """.trimIndent())
//                    System.err.println(execute)
                }
            }.invoke()
        }
    }

}