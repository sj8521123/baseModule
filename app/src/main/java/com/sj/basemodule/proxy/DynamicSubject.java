package com.sj.basemodule.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

//动态代理实例列
public class DynamicSubject implements InvocationHandler {
    //这是动态代理的好处，接受任意类型的对象
    private Object obj;

    public DynamicSubject(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before calling" + method);
        method.invoke(obj, args);
        System.out.println("after calling" + method);
        return null;
    }
}
