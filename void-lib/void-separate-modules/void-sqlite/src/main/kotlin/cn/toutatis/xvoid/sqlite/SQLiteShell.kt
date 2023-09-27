package cn.toutatis.xvoid.sqlite

import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.errorWithModule
import org.sqlite.SQLiteConnection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement


/**
 * SQLite shell
 * sqlite包装工具
 * @property connection 数据库连接
 */
class SQLiteShell(val connection: SQLiteConnection) {

    private val logger = LoggerToolkit.getLogger(this.javaClass)

    /**
     * Select list map
     * 查询Map列表
     * @param sql SQL语句
     * @return 查询Map列表
     */
    fun selectListMap(sql:String):List<Map<String,Any>>{
        val statement: Statement = connection.createStatement()
        val resultSet: ResultSet = statement.executeQuery(sql)
        val list = ArrayList<Map<String, Any>>()
        while (resultSet.next()) {
            val rowMap: MutableMap<String, Any> = HashMap()
            for (i in 1..resultSet.metaData.columnCount) {
                val columnName: String = resultSet.metaData.getColumnName(i)
                val columnValue: Any = resultSet.getObject(i)
                rowMap[columnName] = columnValue
            }
            list.add(rowMap)
        }
        statement.close()
        return list
    }

    /**
     * Select one map
     * 查询单个Map
     * @param sql SQL语句
     * @return 查询Map
     */
    fun selectOneMap(sql:String): Map<String,Any>? {
        val list = selectListMap(sql)
        if (list.size > 1){
            val error = logger.errorWithModule(Meta.MODULE_NAME, Meta.SUB_MODULE_NAME, "查询记录条数大于1")
            throw SQLException(error)
        }
        return if (list.isEmpty()) null else list[0]
    }

}