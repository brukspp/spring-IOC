package com.bruk.framework.mvc.handler;

import com.bruk.framework.mvc.Controller.MapperHandle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.lang.reflect.Method;

public class BaseHandleMapper implements MapperHandle {
    public Map<String,Object> interceptList = new ConcurrentHashMap<>();

    public boolean handle(String url) throws  Exception{
        if(this.interceptList.containsKey(url)){
            Method method = (Method)interceptList.get(url);
            method.invoke(this);
            return true;
        }
        return false;
    }

    public void addIntercept(String intercept , Method method){
        this.interceptList.put(intercept , method);
    }
}
