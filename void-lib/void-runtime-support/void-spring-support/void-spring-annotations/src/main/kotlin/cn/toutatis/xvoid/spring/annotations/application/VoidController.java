package cn.toutatis.xvoid.spring.annotations.application;


import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * controller层聚合注解
 * @author Toutatis_Gc
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@RestController
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public @interface VoidController { }
