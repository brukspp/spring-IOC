package com.bruk.framework.mybean;
import java.beans.BeanInfo;
import java.io.InputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import java.util.Map;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class XmlPropertlyRead {
    private String xmlpath;
    private Class<?> cls;
    private ClassLoader classLoader;
    private Map<String,Object> beanDefine = new ConcurrentHashMap<>(256);
    private Object instance;

    public static final String NAME_ATTRIBUTE = "name";
    public static final String BEAN_ELEMENT = "bean";
    public static final String ID_ATTRIBUTE = "id";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VALUE_ATTRIBUTE = "value";


    private static class XmlPropertlyReadHolder{
        private final static XmlPropertlyRead instance = new XmlPropertlyRead();
    }

    public XmlPropertlyRead(String xmlPath , Class<?> cls){
        this.cls         = cls;
        this.xmlpath    = xmlPath;
        classLoader     = getDefaultClassLoader();
    }

    public XmlPropertlyRead(){
        classLoader     = getDefaultClassLoader();
    }

    public final static XmlPropertlyRead getInstance(){
        return XmlPropertlyReadHolder.instance;
    }

    public void setXmlpath(String xmlpath ){
        this.xmlpath = xmlpath;
    }

    private ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = this.getClass().getClassLoader();
            if (cl == null) {
                // getClassLoader() returning null indicates the bootstrap ClassLoader
                try {
                    cl = ClassLoader.getSystemClassLoader();
                }
                catch (Throwable ex) {
                    // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
                }
            }
        }
        return cl;
    }


    private InputStream getInputStream() throws IOException {
        if(cls != null)
            return this.cls.getResourceAsStream(this.xmlpath);
        else
            return classLoader.getResourceAsStream(this.xmlpath);
    }

    private Document loadDocument() throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(getInputStream());
    }

    public void initBeanDefinitions() throws Exception{
        Element root = loadDocument().getDocumentElement();
        NodeList nl = root.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                if (ele.getNodeName().equals(BEAN_ELEMENT) || ele.getLocalName().equals(BEAN_ELEMENT)) {
                    processBeanDefinition(ele);
                }
            }
        }
    }

    private void processBeanDefinition(Element node) throws Exception{
        String id = node.getAttribute(ID_ATTRIBUTE);
        String classsname = node.getAttribute(CLASS_ATTRIBUTE);
       // System.out.println("id="+id+"  class="+classsname);
        if(classsname != null && classsname.length() > 0 ){
            BeanElement bean = new BeanElement();
            Class<?> cls = Class.forName(classsname);
            bean.setCls(Class.forName(classsname));
            parsePropertyElements(node , bean);
            beanDefine.put(id,bean);
        }
    }

    private void parsePropertyElements(Element beanEle , BeanElement ment){
        NodeList list = beanEle.getChildNodes();

        for(int i =0 ; i < list.getLength() ; i++){
            Node node = list.item(i);
            if(node instanceof Element) {
                Element ele = (Element) node;
               // System.out.println("hehe  " + ele.getNodeName()+"  PROPERTY_ELEMENT="+PROPERTY_ELEMENT);

                if (ele.getNodeName().equals(PROPERTY_ELEMENT)) {
                    String propertyName = ele.getAttribute(NAME_ATTRIBUTE);
                    String propertyValue = parseValue(ele);
                   // System.out.println("propertyName=" + propertyName + " propertyValue=" + propertyValue);
                    ment.addProperty(propertyName, propertyValue);
                }
            }
        }
    }

    private String parseValue(Element ele){
        NodeList nl = ele.getChildNodes();
        for(int i = 0 ; i < nl.getLength() ; i++){
            Node node = nl.item(i);
            if(node instanceof Element){

                Element elevalue = (Element)node;
                if(elevalue.getNodeName().equals(VALUE_ATTRIBUTE)){
                    StringBuilder sb = new StringBuilder();
                    NodeList str = elevalue.getChildNodes();
                    for (i = 0; i < str.getLength(); i++) {
                        Node item = str.item(i);
                        if ((item instanceof CharacterData && !(item instanceof Comment)) || item instanceof EntityReference) {
                            sb.append(item.getNodeValue());
                        }
                    }
                    return sb.toString().trim();
                }
            }
        }
        return "";
    }

    public Object getBean(String beanId) throws  Exception{
        if(beanDefine.containsKey(beanId)){
            BeanElement ele = (BeanElement)beanDefine.get(beanId);
            Class<?> cls = ele.getCls();
            Object bean = cls.newInstance();
            IOCProperty(bean , ele);
            return bean;
        }else {
            System.out.println("do contain");
            return null;
        }
    }

    private void IOCProperty (Object bean , BeanElement ment) throws Exception{
        BeanInfo info = Introspector.getBeanInfo(ment.getCls());
        PropertyDescriptor descriptor[] = info.getPropertyDescriptors();
        Map<String , String> propertymap = ment.getProperty();
        for(String key : propertymap.keySet()){
            for(PropertyDescriptor des : descriptor){
                if(des.getName().toLowerCase().equals(key.toLowerCase())){
                    Method setvalue = des.getWriteMethod();
                    setvalue.invoke(bean , propertymap.get(key));
                    break;
                }
            }
        }
    }
}
