package com.bruk.framework.mvc.Controller;
import java.lang.reflect.Method;

public interface MapperHandle {
    boolean handle(String url) throws Exception;
    void addIntercept(String interceptName , Method method);
}
