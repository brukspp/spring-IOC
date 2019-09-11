package com.bruk.annotationbean;

import com.bruk.framework.annotation.Bean;

@Bean(value="this is test2",beanName = "test2")
public class test2 {
    public String getMsg(){
    Bean bean = this.getClass().getAnnotation(Bean.class);
    return bean.value();
}
}
