package com.bruk;

import com.bruk.annotationMapperHandle.MapperHandle1;
import com.bruk.annotationMapperHandle.MapperHandle2;
import com.bruk.annotationbean.*;
import com.bruk.framework.annotation.Bean;
import com.bruk.framework.mvc.Controller.AnnotationHandle;
import com.bruk.framework.mvc.Controller.MapperHandle;
import com.bruk.framework.mvc.handler.HandlerManager;
import com.bruk.framework.mvc.servlet.DispatchServlet;
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

           /* MapperHandle1 mapp1 = (MapperHandle1) factory.getBean("mapper1");
            System.out.println("the mapp1 msg is "+mapp1.getMsg());

            MapperHandle2 mapp2 = (MapperHandle2) factory.getBean("mapper2");
            System.out.println("the mapp2 msg is "+mapp2.getMsg());*/

            DispatchServlet servlet = new DispatchServlet();
            servlet.doService("/hello");
            servlet.doService("/world");
            servlet.doService("/spp");
            servlet.doService("/bruk");
            servlet.doService("/test");
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

            //annotation bean load
            loadAnnotation("com/bruk/annotationbean/",factory);
            loadMapperHandle("com/bruk/annotationMapperHandle/",factory);
        }catch (Exception e){
                System.out.print(e.getMessage());
            }
    }

    private static void loadMapperHandle(String path , BeanFactory factory) {
        try {
            List<Class<?>> listclass = AnnotationScanner.scanClass(path);
            if (listclass.size() > 0) {
                for (int i = 0; i < listclass.size(); i++) {
                    Class<?> beanclass = listclass.get(i);
                    if (beanclass.isAnnotationPresent(Bean.class) && beanclass.isAnnotationPresent(AnnotationHandle.class)) {
                        Bean bean = beanclass.getAnnotation(Bean.class);
                        String beanid = bean.beanName();
                        factory.registerBean(beanclass, beanid);

                        MapperHandle obj = (MapperHandle)factory.getBean(beanid);
                        HandlerManager.registerHandle(obj);
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    private static void loadAnnotation(String path , BeanFactory factory) {
        try {
            List<Class<?>> listclass = AnnotationScanner.scanClass(path);
            if (listclass.size() > 0) {
                for (int i = 0; i < listclass.size(); i++) {
                    Class<?> beanclass = listclass.get(i);
                    if (beanclass.isAnnotationPresent(Bean.class)) {
                        Bean bean = beanclass.getAnnotation(Bean.class);
                        String beanid = bean.beanName();
                        factory.registerBean(beanclass, beanid);
                    }
                }
            }
        } catch (Exception e) {

        }
    }
}
