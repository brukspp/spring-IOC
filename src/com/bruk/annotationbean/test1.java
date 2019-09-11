package com.bruk.annotationbean;

import com.bruk.framework.annotation.Bean;
@Bean(value="this is test1",beanName = "test1")
public class test1 {
    public String getMsg(){
        Bean bean = this.getClass().getAnnotation(Bean.class);
        return bean.value();
    }
}
