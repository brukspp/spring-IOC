package com.bruk.framework.mybean;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanParam {
    String who() default "me";
    String want() default "meat";
}
