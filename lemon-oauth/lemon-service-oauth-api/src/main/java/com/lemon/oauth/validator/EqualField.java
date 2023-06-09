package com.lemon.oauth.validator;


import com.lemon.oauth.validator.impl.EqualFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName EqualField
 **/
@Documented
@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = EqualFieldValidator.class)
public @interface EqualField {

    /**
     * 源属性
     */
    String srcField();

    /**
     * 目标属性
     */
    String dstField();

    String message() default "the two fields must be equal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
