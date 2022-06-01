package cn.toutatis.redis.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Toutatis_Gc
 * @date 2022/5/21 21:06
 */
@Aspect
public class VoidRedisTransactionAspect {

    @Pointcut("execution(* cn.toutatis.redis.client.inherit.jedis.AOPKotlinTest.test(..))")
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
