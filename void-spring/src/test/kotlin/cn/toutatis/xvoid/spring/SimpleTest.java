package cn.toutatis.xvoid.spring;

import cn.toutatis.xvoid.spring.support.amqp.entity.SystemLog;
import cn.toutatis.xvoid.spring.support.amqp.log.LogType;
import org.junit.Test;

public class SimpleTest {

    @Test
    public void testSimple() {
        SystemLog systemLog = new SystemLog();
        systemLog.setType(LogType.ADMIN.name());
        System.out.println("testSimple");
    }

}
