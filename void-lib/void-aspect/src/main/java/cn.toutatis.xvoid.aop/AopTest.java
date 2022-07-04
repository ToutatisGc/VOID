package cn.toutatis.xvoid.aop;

public class AopTest {

    public void test(){
        System.err.println(7773);
    }

    public static void main(String[] args) {
//        AopTest aNew = AopTest::new;
        System.err.println();
        new AopTest().test();
    }

}
