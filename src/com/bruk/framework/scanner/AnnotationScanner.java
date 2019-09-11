package com.bruk.framework.scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Enumeration;
import java.net.URL;
import java.io.File;

public class AnnotationScanner {
    public static List<Class<?>> scanClass(String packageName) throws Exception{
        List<Class<?>> listclass = new ArrayList<>();
        Enumeration<URL> resource = Thread.currentThread().getContextClassLoader().getSystemResources(packageName);
        while(resource.hasMoreElements()){
            URL source = resource.nextElement();
            if(source.getProtocol().equals("file")){
                File files[] = new File(source.getFile()).listFiles();
                for(int i = 0 ; i < files.length ; i++){
                    String filename = files[i].getName();
                    int length = filename.length();
                    String classname = packageName.replace("/",".")+filename.substring(0 , length - 6);
                    listclass.add(Class.forName(classname));
                }
            }

        }
        return listclass;
    }
}
