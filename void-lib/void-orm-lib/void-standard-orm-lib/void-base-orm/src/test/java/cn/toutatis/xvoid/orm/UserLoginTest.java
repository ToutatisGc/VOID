package cn.toutatis.xvoid.orm;


import cn.toutatis.xvoid.common.enums.RegistryType;
import org.junit.Test;

public class UserLoginTest {

    @Test
    public void testUidGenerate(){
//        for (int i = 10; i < 100; i++) {
//            int twoDigitNumber = i; // 4 + 7 = 11, 转换后为 1
//            int oneDigitNumber = (twoDigitNumber / 10 + twoDigitNumber % 10) % 10;
//            System.err.println(i+"="+oneDigitNumber);
//        }
//
//        char[] chars = new char[]{'E','T','A','P','W','Q','Z','O'};
//        for (int i = 0; i < chars.length; i++) {
//            char c = chars[i];
//            int oneDigitNumber = (c / 10 + c % 10) % 10;
//            System.err.println(c+"="+oneDigitNumber);
//        }


//        for (int i = 0; i < 100; i++) {
//            String s = RandomStringUtils.randomAlphabetic(3).toUpperCase(Locale.ROOT);
//            char c1 = s.charAt(0);
//            char c2 = s.charAt(1);
//            char c3 = s.charAt(2);
//            System.err.println(s+"="+(c1+c2+c3/ 10 + c1+c2+c3 % 10));
//        }

        for (RegistryType value : RegistryType.values()) {
            int ix = 0;
            for (int i = 0; i < value.name().length(); i++) {
                char c = value.name().charAt(i);
                ix+=c;
            }
            System.err.println((ix/ 10 + ix % 10));
        }
    }

}
