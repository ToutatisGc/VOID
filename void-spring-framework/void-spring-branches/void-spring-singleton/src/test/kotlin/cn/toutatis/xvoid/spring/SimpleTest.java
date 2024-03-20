package cn.toutatis.xvoid.spring;

import cn.toutatis.xvoid.common.standard.StandardFields;
import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog;
import cn.toutatis.xvoid.orm.base.infrastructure.enums.LogType;
import cn.toutatis.xvoid.project.spring.entities.SqliteVoidContext;
import cn.toutatis.xvoid.sql.dql.DQLBuilder;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SimpleTest {

    @Test
    public void testSimple() {
        SystemLog systemLog = new SystemLog();
        systemLog.setType(LogType.ADMIN.name());
        System.out.println("testSimple");
    }

    @Test
    public void testStopTime(){
        StopWatch started = StopWatch.createStarted();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        started.stop();
        System.err.println(started.formatTime());
    }

    @Test
    public void generatePassword(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("a12345678");
        System.err.println(encode);
    }

    @Test
    public void testBuildSQL() throws Exception {
        DQLBuilder<SqliteVoidContext> dictDQLBuilder = new DQLBuilder<>(SqliteVoidContext.class,true);
        dictDQLBuilder.eq(SqliteVoidContext::getMchId, StandardFields.VOID_BUSINESS_DEFAULT_CREATOR);
        String build = dictDQLBuilder.build();
    }

}
