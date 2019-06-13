package com.sj.basemodule.model.request;


import com.app.idea.utils.SharedPreferencesHelper;
import com.app.idea.utils.Util;

/**
 * Created by zhpan on 2017/10/25.
 * Description:
 */

public class BasicRequest {
    public String token = (String) SharedPreferencesHelper.get(Util.getContext(), "token", "");

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
