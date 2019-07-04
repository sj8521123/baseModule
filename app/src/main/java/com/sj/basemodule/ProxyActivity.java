package com.sj.basemodule;

import android.util.Log;

import com.sj.basemodule.proxy.DynamicSubject;
import com.sj.basemodule.proxy.RealSubject;
import com.sj.basemodule.proxy.Subject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import basemodule.sj.com.basic.base.BaseActivity;

public class ProxyActivity extends BaseActivity {
    private static final String TAG = "ProxyActivity";

    @Override
    protected void reConnect() {

    }

    @Override
    public int initLayout() {
        return R.layout.activity_proxy;
    }

    @Override
    public void initFromData() {

    }

    @Override
    public void initLayoutView() {

    }

    @Override
    public void initLocalData() {
        RealSubject realSubject = new RealSubject();
        //被代理的类
        Subject rs = new RealSubject();
        InvocationHandler ds = new DynamicSubject(rs);
        Class<?> cls = rs.getClass();

        //一次性生成代理
        Subject subject = (Subject) Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), ds);

        //这里可以通过运行结果证明subject是Proxy的一个实例，这个实例实现了Subject接口
        System.out.println(subject instanceof Proxy);

        //这里可以看出subject的Class类是$Proxy0,这个$Proxy0类继承了Proxy，实现了Subject接口
        System.out.println("subject的Class类是：" + subject.getClass().toString());

        System.out.print("subject中的属性有：");

        Field[] field = subject.getClass().getDeclaredFields();
        for (Field f : field) {
            System.out.print(f.getName() + ", ");
        }

        System.out.print("\n" + "subject中的方法有：");

        Method[] method = subject.getClass().getDeclaredMethods();

        for (Method m : method) {
            System.out.print(m.getName() + ", ");
        }

        System.out.println("\n" + "subject的父类是：" + subject.getClass().getSuperclass());

        System.out.print("\n" + "subject实现的接口是：");

        Class<?>[] interfaces = subject.getClass().getInterfaces();

        for (Class<?> i : interfaces) {
            System.out.print(i.getName() + ", ");
        }

        System.out.println("\n\n" + "运行结果为：");
        subject.request();
    }
}
