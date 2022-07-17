package com.lemon.interceptors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ScopeLevel
 * @Description 权限校验注解，放在方法上或者类上面。只要有该注解请求就会通过拦截器，能够获取LocalUser中用户的信息
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ScopeLevel {
    int value() default 4;
}