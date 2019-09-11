package com.bruk.framework.mvc.handler;

import com.bruk.framework.mvc.Controller.MapperHandle;
import com.bruk.framework.annotation.BeanMethod;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;

public class HandlerManager {
    public static List<MapperHandle> handlemap = new ArrayList<>();

    public static void registerHandle(MapperHandle handle){
        Class<?> cls = handle.getClass();
        Method methods[] = cls.getDeclaredMethods();
        for(Method method : methods){
            if(method.isAnnotationPresent(BeanMethod.class)){
                String intercept = method.getAnnotation(BeanMethod.class).value();
                handle.addIntercept(intercept,method);
            }
        }
        handlemap.add(handle);
    }
}
