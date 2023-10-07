package cn.toutatis.xvoid.sql.dql;

import cn.toutatis.xvoid.sql.base.SQLType;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestJava {

    @Test
    public void testGetEntityTableName() {
        DQLMetaBuilder<TestTableMp> mpTest = new DQLMetaBuilder<>(SQLType.SELECT, TestTableMp.class);
        Assertions.assertEquals("test_mp", mpTest.getTable());
        DQLMetaBuilder<TestTableOwn> ownTest = new DQLMetaBuilder<>(SQLType.SELECT, TestTableOwn.class);
        Assertions.assertEquals("test_own", ownTest.getTable());
        DQLMetaBuilder<TestTableJava> nilTest = new DQLMetaBuilder<>(SQLType.SELECT, TestTableJava.class);
        Assertions.assertEquals("test_table_java", nilTest.getTable());
    }

    @Test
    public void testBuilder() throws Exception {
        DQLBuilder<TestTableJava> dqlBuilder = new DQLBuilder<>(TestTableJava.class);
        dqlBuilder.select(TestTableJava::getAge);
        dqlBuilder.eq(TestTableJava::getAge,18);
        dqlBuilder.eq(TestTableJava::getName,"foo");
        System.err.println(dqlBuilder.build());
    }
}
