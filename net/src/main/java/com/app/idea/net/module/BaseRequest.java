package com.app.idea.net.module;


import basemodule.sj.com.basic.config.SPUtils;
import basemodule.sj.com.basic.util.Util;

/**
 * Created by jokerlee on 16/9/28.
 */
public class BaseRequest {
    public String token;


    public BaseRequest() {
        SPUtils.getInstance(Util.getCurrentUserPrefsName()).get("token", "");
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void updateToken(String token) {
        this.token = token;
    }
}
