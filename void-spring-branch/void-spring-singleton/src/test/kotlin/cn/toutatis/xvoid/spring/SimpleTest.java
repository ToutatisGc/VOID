package cn.toutatis.xvoid.spring;

import cn.toutatis.xvoid.orm.base.authentication.entity.SystemUserLogin;
import cn.toutatis.xvoid.orm.base.infrastructure.entity.SystemLog;
import cn.toutatis.xvoid.orm.base.infrastructure.enums.LogType;
import cn.toutatis.xvoid.toolkit.clazz.LambdaToolkit;
import cn.toutatis.xvoid.toolkit.clazz.XFunc;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Function;
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

}
