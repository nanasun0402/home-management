package com.caring.wxrs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author james
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface JsonProperty {

    String comment() default "";

    Class affectClass();

    String[] excludeProperties() default {};

    String[] includeProperties() default {};
}
