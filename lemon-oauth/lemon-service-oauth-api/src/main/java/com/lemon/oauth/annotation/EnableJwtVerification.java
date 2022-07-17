package com.lemon.oauth.annotation;

import com.lemon.oauth.config.MvcConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName annotation
 * @Description 启用JWT验证开关，会通过mvc的拦截器拦截用户请求，并获取用户信息，存入LocalUser
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(MvcConfiguration.class)
@Documented
@Inherited
public @interface EnableJwtVerification {
}
