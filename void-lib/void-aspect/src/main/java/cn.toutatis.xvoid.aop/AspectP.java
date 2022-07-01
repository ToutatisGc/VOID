package cn.toutatis.xvoid.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AspectP {

    @Pointcut("execution(public * *(..))")
    public void pointCut(){}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint po) {
        System.err.println(666);
        try {
            po.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

}
