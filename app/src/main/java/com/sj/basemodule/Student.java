package com.sj.basemodule;

public class Student {
    private static class singleHander{
        private static Student student = new Student();
    }
    public static Student getInstance(){
        return  singleHander.student;
    }
}
