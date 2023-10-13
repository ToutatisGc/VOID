package cn.toutatis.xvoid.common.annotations.database;

import java.lang.annotation.*;

/**
 * 表名
 * @author Toutatis_Gc
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface DDLTable {

    String value() default "";

}
