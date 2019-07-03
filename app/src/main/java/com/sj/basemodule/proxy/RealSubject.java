package com.sj.basemodule.proxy;

//真实角色：实现Subject的request方法
public class RealSubject implements Subject {
    @Override
    public void request() {
        System.out.println("From real SUbject");
    }
}
