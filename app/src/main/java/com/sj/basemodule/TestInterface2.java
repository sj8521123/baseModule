package com.sj.basemodule;

public interface TestInterface2 {
    public abstract void run2();
    public abstract void eat2();
    public static final int a= 10;

    default  String test(String c){
        int b  = 10;
        c = c +"abc";
        return  c;
    }
}
