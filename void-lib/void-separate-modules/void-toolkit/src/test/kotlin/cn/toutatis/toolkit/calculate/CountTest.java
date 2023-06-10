package cn.toutatis.toolkit.calculate;

import org.junit.Test;

public class CountTest {

    @Test
    public void test1(){
        double a = 0.75;
        double h = 8;
        double base = 0;

        double zipTimes = 3;

        for (double x = zipTimes; x > 0; x -= 1) {
            double y = (a * Math.pow(x - h, 2) + base)/100;
            System.out.println("x = " + x + ", y = " + y);
        }
    }

}
