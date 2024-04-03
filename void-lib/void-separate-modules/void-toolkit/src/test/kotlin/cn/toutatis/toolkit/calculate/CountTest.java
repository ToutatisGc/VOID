package cn.toutatis.toolkit.calculate;

import cn.toutatis.xvoid.toolkit.number.NumberToolkit;
import cn.toutatis.xvoid.toolkit.digest.DigestToolkit;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

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

    @Test
    public void test2(){
        System.err.println(DigestToolkit.sha256("test"));
        System.err.println(DigestToolkit.sha256("test"));
    }

    @Test
    public void test3(){
        BigDecimal a1 = new BigDecimal("-10.5");
        Assert.assertFalse(NumberToolkit.isInteger(a1));
        BigDecimal a2 = new BigDecimal("-1999");
        Assert.assertTrue(NumberToolkit.isInteger(a2));
        BigDecimal a3 = new BigDecimal("100.5355353");
        Assert.assertFalse(NumberToolkit.isInteger(a3));
        BigDecimal a4 = new BigDecimal("0");
        Assert.assertTrue(NumberToolkit.isInteger(a4));
    }

}
