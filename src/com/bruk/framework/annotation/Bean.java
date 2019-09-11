package com.bruk.framework.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
    String value() default "no Msg";
    String beanName() default "beanName";
}
