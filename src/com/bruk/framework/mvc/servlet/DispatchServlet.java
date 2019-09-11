package com.bruk.framework.mvc.servlet;

import com.bruk.framework.mvc.Controller.MapperHandle;
import com.bruk.framework.mvc.handler.HandlerManager;

public class DispatchServlet {
    public void doService(String url) {
        try {
            for (MapperHandle handle : HandlerManager.handlemap) {
                if (handle.handle(url)) {
                    return;
                }
            }
            System.out.println("not support this command");
        } catch (Exception e) {

        }
    }
}
