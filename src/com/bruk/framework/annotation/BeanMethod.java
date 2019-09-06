package com.bruk.framework.mybean;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeanMethod {
    String name() default "noName";
}
