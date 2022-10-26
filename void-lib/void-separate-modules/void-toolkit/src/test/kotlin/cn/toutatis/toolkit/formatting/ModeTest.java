package cn.toutatis.toolkit.formatting;

import cn.toutatis.xvoid.toolkit.formatting.EnumToolkit;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Toutatis_Gc
 * @date 2022/10/26 21:22
 */
public class ModeTest {


    @Test(expected = IllegalArgumentException.class)
    public void enTest(){
        RunMode normal = EnumToolkit.getValue(RunMode.class,"AA");
        Assert.assertNotNull("正常有值:",normal);
        RunMode nullEnum = EnumToolkit.getValue(RunMode.class,"AAA");
        Assert.assertNull("期待NULL:",nullEnum);
        EnumToolkit.getValue(RunMode.class,"AAA",false);
    }

}
