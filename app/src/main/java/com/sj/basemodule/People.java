package com.sj.basemodule;

public class People {
    private int age;
    private String name;

    public People() {
    }

    public People(int age, String name) {
        this.name  = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
