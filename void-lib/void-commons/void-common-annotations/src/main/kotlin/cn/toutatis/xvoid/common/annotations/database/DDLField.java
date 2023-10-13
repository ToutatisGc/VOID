package cn.toutatis.xvoid.common.annotations.database;

import java.lang.annotation.*;

/**
 * 实体类字段注解
 * @author Toutatis_Gc
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DDLField {

    /**
     * 字段值
     * @return 字段名
     */
    String name();

    /**
     * 字段是否在表中存在
     * @return 标记字段是否在表中存在
     */
    boolean exist() default true;

}
