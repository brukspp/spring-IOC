package com.bruk.framework.mybean;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.bruk.framework.mybean.Bean;
public class BeanFactory {
    private static Map<String,Object> beanMap = new ConcurrentHashMap<>(256);
    private XmlPropertlyRead xmlread;

    public BeanFactory(){
        xmlread = XmlPropertlyRead.getInstance();
    }

    public Object getBean(String beanName) throws Exception{
        if(beanMap.containsKey(beanName)){
            return beanMap.get(beanName);
        }else{
            Object bean = xmlread.getBean(beanName);
            if(bean == null){
                return null;
            }
            beanMap.put(beanName,bean);
            return bean;
        }
    }

    public void registerBean(Class<?> cls, String beanName) throws Exception{
        Object beanObj = (Object)cls.newInstance();
        beanMap.put(beanName,beanObj);
    }

    public void annotationRegisterBean(Class<? extends Bean> cls , String beanName) throws Exception{
        if(!cls.isAnnotationPresent(Bean.class)){
            return;
        }
        Object beanOjb = cls.newInstance();
    }
}
