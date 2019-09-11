package com.bruk.annotationMapperHandle;

import com.bruk.framework.annotation.Bean;
import com.bruk.framework.annotation.BeanMethod;
import com.bruk.framework.mvc.Controller.AnnotationHandle;
import com.bruk.framework.mvc.handler.BaseHandleMapper;

@Bean(value = "handle mapper1",beanName = "mapper1")
@AnnotationHandle()
public class MapperHandle1 extends BaseHandleMapper {


    @BeanMethod("/hello")
    public void doHandle1(){
        System.out.println("hello");
    }

    @BeanMethod("/world")
    public void doHandle2(){
        System.out.println("world");
    }

    public String getMsg(){
        Bean bean = this.getClass().getAnnotation(Bean.class);
        return bean.value();
    }
}
