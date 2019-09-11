package com.bruk.annotationbean;

import com.bruk.framework.annotation.Bean;

@Bean(value = "this is simpleAnno",beanName = "simple")
public class SimpleAnnotationBean {
    public String getMsg(){
        Bean bean = this.getClass().getAnnotation(Bean.class);
        return bean.value();
    }
}
