package cn.toutatis.toolkit.clazz;

import org.junit.Test;

public class JTest {

    @Test
    public void test(){
        TestEntity testEntity = new TestEntity();
        testEntity.setName("toutatis");
        testEntity.setAge(18);
        System.out.println(testEntity);
    }
}
