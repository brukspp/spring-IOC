package com.bruk;
public class HelloSpp{
    String Msg;

    public void setMsg(String msg){
        this.Msg = msg;
    }

    public String getMsg(){
        return this.Msg;
    }

    public void sayHello(){
        System.out.print(Msg);
    }
}
