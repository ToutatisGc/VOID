package cn.toutatis.xvoid.support.spring.annotations;

import java.lang.annotation.*;

/**
 * @author Toutatis_Gc
 * 日志捕获注解
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Inherited
@Documented
public @interface LogHandle {

    String value() default "";

}
