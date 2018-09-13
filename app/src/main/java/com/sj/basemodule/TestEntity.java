package com.sj.basemodule;

/**
 * Created by 13658 on 2018/8/8.
 */

public class TestEntity {
    public TestEntity(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
