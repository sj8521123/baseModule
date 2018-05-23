package com.sj.basemodule.net.response.base;

/**
 * content:所有返回的response基础类，方便统一的处理
 * author：sj
 * time: 2017/11/28 16:31
 * email：13658029734@163.com
 * phone:13658029734
 */

public class ResponseBaseModel<T> {
    private String code;
    private String level;
    private String message;
    private boolean status;

    private T data;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class DataBean {
    }
}
