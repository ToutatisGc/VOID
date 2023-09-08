package cn.toutatis.xvoid.sqlite

import org.sqlite.SQLiteConnection
import java.sql.ResultSet
import java.sql.Statement

class SQLiteShell(val connection: SQLiteConnection) {

    fun select(sql:String){
        val statement: Statement = connection.createStatement()
        statement.setQueryTimeout(30) // set timeout to 30 sec.

        statement.executeUpdate("drop table if exists person")
        statement.executeUpdate("create table person (id integer, name string)")
        statement.executeUpdate("insert into person values(1, 'leo')")
        statement.executeUpdate("insert into person values(2, 'yui')")
        val rs: ResultSet = statement.executeQuery("select * from person")
        while (rs.next()) {
            // read the result set
            System.out.println("name = " + rs.getString("name"))
            System.out.println("id = " + rs.getInt("id"))
        }
        connection.close()
    }

}