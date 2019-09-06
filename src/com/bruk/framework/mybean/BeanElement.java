package com.bruk.framework.mybean;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class BeanElement {
    private Class<?> cls;
    private Map<String,String> property = new ConcurrentHashMap<>();

    void setCls(Class<?> cls){
        this.cls = cls;
    }

    public Class<?> getCls() {
        return cls;
    }

    void addProperty(String key , String value){
        property.put(key , value);
    }

    Map getProperty(){
        return property;
    }
}
