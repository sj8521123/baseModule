package com.sj.basemodule;

public interface TestInterface {
    public abstract void run();

    public abstract void eat();

    public static final int a = 10;

    default String test(String c) {
        int b = 10;
        c = c + "abc";
        return c;
    }

    default String test2(String c) {
        int b = 10;
        c = c + "abc";
        return c;
    }
}
