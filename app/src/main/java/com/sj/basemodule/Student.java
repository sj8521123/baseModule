package com.sj.basemodule;

public class Student implements TestInterface, TestInterface2 {

    @Override
    public void run() {

    }

    @Override
    public  void eat() {

    }

    @Override
    public String test(String c) {
        return TestInterface.super.test(c);
    }

    @Override
    public void run2() {

    }

    @Override
    public void eat2() {

    }

    public final void a() {

    }

    public final void a(String a) {

    }
}
