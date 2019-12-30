package com.sj.basemodule;

public class Student {
    public StringBuilder test(StringBuilder x) {
        Integer i = Integer.valueOf(x.toString());
        x.setLength(0);
        x.append(i + 2);
        return x;
    }
}