package cn.toutatis.xvoid.sql.dql

import cn.toutatis.xvoid.sql.base.SQLType
import org.junit.Assert
import org.junit.jupiter.api.Test

class DSLTest {

    @Test
    fun `test get entity table name`(){
        val mpTest = DQLMetaBuilder(SQLType.SELECT, TestTableMp::class.java)
        Assert.assertEquals("test_mp",mpTest.table)
        val ownTest = DQLMetaBuilder(SQLType.SELECT, TestTableOwn::class.java)
        Assert.assertEquals("test_own",ownTest.table)
        val nilTest = DQLMetaBuilder(SQLType.SELECT, TestTableJava::class.java)
        Assert.assertEquals("test_table_java",nilTest.table)
    }

    @Test
    fun `test generate sql`(){
        val dqlBuilder0 = DQLBuilder(TestTableJava::class.java)
        dqlBuilder0.eq(TestTableJava::getName,"foo")
        val build = dqlBuilder0.build()
        System.err.println(build)

        val dqlBuilder1 = DQLBuilder(TestTableJava::class.java)
        dqlBuilder1.select("name","username")
        dqlBuilder1.select("age","user_age")
        System.err.println(dqlBuilder1.build())
//        dslBuilder.
//        dslBuilder.
//        dslBuilder.eq()
//        val func = LambdaHandler.serialize(TestTableJava::getName)
//        System.err.println(func)
//        System.err.println(LambdaHandler.getFieldName(func))
    }

}