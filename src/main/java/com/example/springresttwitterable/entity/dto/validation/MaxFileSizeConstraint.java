package com.example.springresttwitterable.entity.dto.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

/**
 * Created on 11/20/18.
 * <p>
 * @author Vlad Martinkov
 */

@Documented
@Constraint(validatedBy = MaxFileSizeValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxFileSizeConstraint
{

    String message() default "Please, don't upload files with size more than 500 kB!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
