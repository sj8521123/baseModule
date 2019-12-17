package com.sj.basemodule;

public class Student {
    public StringBuilder test(StringBuilder x) {
        Integer i = Integer.valueOf(x.toString());
        x.setLength(0);
        x.append(i+2);
        return x;

    }
    /*private static class singleHander{
        private static Student student = new Student();
    }
    public static Student getInstance(){
        return  singleHander.student;
    }*/
}
