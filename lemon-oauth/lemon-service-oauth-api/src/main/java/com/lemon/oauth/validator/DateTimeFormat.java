package com.lemon.oauth.validator;


import com.lemon.oauth.validator.impl.DateTimeFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName DateTimeFormat
 * 日期格式校验器 对 String 类型进行校验，是否匹配给定的模式 默认校验日期格式 yyyy-MM-dd HH:mm:ss
 **/
@Documented
@Retention(RUNTIME)
@Target({FIELD, TYPE_USE, TYPE_PARAMETER})
@Constraint(validatedBy = DateTimeFormatValidator.class)
public @interface DateTimeFormat {

    String message() default "date pattern invalid";

    String pattern() default "yyyy-MM-dd HH:mm:ss";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
