package cn.toutatis.xvoid.sql.dql;

import cn.hutool.core.lang.func.Func1;
import cn.toutatis.xvoid.sql.base.SQLType;
import cn.toutatis.xvoid.sql.convert.ResultObjectMapperConverter;
import cn.toutatis.xvoid.toolkit.clazz.LambdaToolkit;
import cn.toutatis.xvoid.toolkit.clazz.XFunc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;
import java.util.Map;

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
    public void testConditionsBuilder() throws Exception {
        DQLBuilder<Object> objectDQLBuilder = new DQLBuilder<>("test_table_java_name");
        Assertions.assertEquals("SELECT * FROM test_table_java_name", objectDQLBuilder.build());
        System.err.println(objectDQLBuilder.build());
        DQLBuilder<TestTableJava> dqlBuilder1 = new DQLBuilder<>(TestTableJava.class);
        dqlBuilder1.select(TestTableJava::getAge);
        dqlBuilder1.eq(TestTableJava::getName,"foo");
        System.err.println(dqlBuilder1.build());

        DQLBuilder<TestTableJava> dqlBuilder = new DQLBuilder<>(TestTableJava.class);
        dqlBuilder.select(TestTableJava::getAge);
        dqlBuilder.eq(TestTableJava::getAge,18);
        dqlBuilder.eq(TestTableJava::getName,"foo");
        System.err.println(dqlBuilder.build());
    }

    @Test
    public void testSelectBuilder() throws Exception {
        DQLBuilder<TestTableJava> dqlBuilder1 = new DQLBuilder<>(TestTableJava.class);
        System.err.println(dqlBuilder1.build());

        DQLBuilder<TestTableJava> dqlBuilder = new DQLBuilder<>(TestTableJava.class);
        dqlBuilder.select(TestTableJava::getAge);
        dqlBuilder.select("name","username");
        System.err.println(dqlBuilder.build());
        XFunc<TestTableOwn, String> name = TestTableOwn::getName;
        LambdaToolkit.getFieldName(name);

        String fieldName = LambdaToolkit.getFieldName((XFunc<TestTableOwn, String>)TestTableOwn::getName);
        String fieldName1 = LambdaToolkit.getFieldName(TestTableOwn::getName);
        System.err.println(fieldName1);
    }

    @Test
    public void testDistinct() throws Exception {
        DQLBuilder<TestTableJava> dqlBuilder1 = new DQLBuilder<>(TestTableJava.class);
        dqlBuilder1.distinct();
        System.err.println(dqlBuilder1.build());

        DQLBuilder<TestTableJava> dqlBuilder = new DQLBuilder<>(TestTableJava.class);
        dqlBuilder.select(TestTableJava::getAge);
        dqlBuilder.select("name","username");
        dqlBuilder.distinct();
        System.err.println(dqlBuilder.build());
    }

    @Test
    public void testChild(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            DQLBuilder<TestTableJava> dqlBuilderChild = new DQLBuilder<>(TestTableJava.class);
            DQLBuilder<TestTableJava> dqlBuilder1 = new DQLBuilder<>(TestTableJava.class);
            dqlBuilder1.selectChild(dqlBuilderChild,"child");
        });
        DQLBuilder<TestTableJava> dqlBuilderChild = new DQLBuilder<>(TestTableJava.class);
        dqlBuilderChild.select("name");
        DQLBuilder<TestTableJava> dqlBuilder1 = new DQLBuilder<>(TestTableJava.class);
        dqlBuilder1.selectChild(dqlBuilderChild,"child");
        System.err.println(dqlBuilder1);
    }

    @Test
    public void testConvert(){
        ResultObjectMapperConverter con = ResultObjectMapperConverter.instance();
        Map<String, Object> info = Map.of("NAME", "foo");
        TestTableJava convert = con.convert(info, TestTableJava.class);
        System.err.println(convert);
    }
}
