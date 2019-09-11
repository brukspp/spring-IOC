package com.bruk;

import com.bruk.annotationbean.*;
import com.bruk.framework.annotation.Bean;
import com.bruk.framework.mybean.XmlPropertlyRead;
import com.bruk.framework.mybean.BeanFactory;
import com.bruk.framework.scanner.AnnotationScanner;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        BeanFactory factory = new BeanFactory();
        initbean(factory);
        try {
            HelloSpp spp = (HelloSpp) factory.getBean("helloWorld");
            System.out.println("the ioc is"+spp.getMsg());

            SimpleAnnotationBean test = (SimpleAnnotationBean) factory.getBean("simple");
            System.out.println("the test msg is "+test.getMsg());

            test1 test1 = (test1) factory.getBean("test1");
            System.out.println("the test1 msg is "+test1.getMsg());

            test2 test2 = (test2) factory.getBean("test2");
            System.out.println("the test2 msg is "+test2.getMsg());
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
    }

    private static  void  initbean(BeanFactory factory){
        try {
            XmlPropertlyRead reader;
            reader = XmlPropertlyRead.getInstance();
            reader.setXmlpath("beans.xml");
            reader.initBeanDefinitions();

            List<Class<?>> listclass = AnnotationScanner.scanClass("com/bruk/annotationbean/");
            if (listclass.size() > 0){
                for(int i =0 ; i < listclass.size() ; i++) {
                    Class<?> beanclass = listclass.get(i);
                    Bean bean = beanclass.getAnnotation(Bean.class);
                    String beanid = bean.beanName();
                    factory.registerBean(beanclass, beanid);
                }
            }
        }catch (Exception e){
                System.out.print(e.getMessage());
            }
    }
}
