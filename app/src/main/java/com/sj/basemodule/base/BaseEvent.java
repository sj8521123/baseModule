package com.sj.basemodule.base;

/**
 * Event基类
 * <p/>
 * EventBus使用所有Event继承此类
 * <p/>
 */
public class BaseEvent {

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
