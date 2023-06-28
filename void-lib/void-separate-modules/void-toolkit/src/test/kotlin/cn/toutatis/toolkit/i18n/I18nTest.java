package cn.toutatis.toolkit.i18n;

import cn.toutatis.xvoid.toolkit.locale.I18n;
import org.junit.Test;

import java.util.Locale;

public class I18nTest {

    @Test
    public void test1(){
        I18n.setLocale(Locale.SIMPLIFIED_CHINESE);
    }

}
