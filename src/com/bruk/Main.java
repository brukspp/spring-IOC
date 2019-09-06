package com.bruk;

import com.bruk.framework.mybean.XmlPropertlyRead;
import com.bruk.framework.mybean.BeanFactory;
public class Main {

    public static void main(String[] args) {
	// write your code here
        try {
            XmlPropertlyRead reader = XmlPropertlyRead.getInstance();
            reader.setXmlpath("beans.xml");
            reader.initBeanDefinitions();
            BeanFactory factory = new BeanFactory();
            HelloSpp spp = (HelloSpp) factory.getBean("helloWorld");

            System.out.print("the ioc is"+spp.getMsg());
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
    }
}
