package cn.toutatis.xvoid.spring;

import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog;
import cn.toutatis.xvoid.orm.base.infrastructure.enums.LogType;
import org.junit.Test;

public class SimpleTest {

    @Test
    public void testSimple() {
        SystemLog systemLog = new SystemLog();
        systemLog.setType(LogType.ADMIN.name());
        System.out.println("testSimple");
    }

}
