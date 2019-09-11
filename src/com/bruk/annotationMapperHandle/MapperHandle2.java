package com.bruk.annotationMapperHandle;
   
import com.bruk.framework.annotation.Bean;
import com.bruk.framework.annotation.BeanMethod;
import com.bruk.framework.mvc.Controller.AnnotationHandle;
import com.bruk.framework.mvc.handler.BaseHandleMapper;

@Bean(value = "handle mapper2",beanName = "mapper2")
@AnnotationHandle()
public class MapperHandle2 extends BaseHandleMapper {
    
    @BeanMethod("/spp")
    public void doHandle1(){
        System.out.println("spp");
    }

    @BeanMethod("/bruk")
    public void doHandle2(){
        System.out.println("bruk");
    }

    public String getMsg(){
        Bean bean = this.getClass().getAnnotation(Bean.class);
        return bean.value();
    }
}
