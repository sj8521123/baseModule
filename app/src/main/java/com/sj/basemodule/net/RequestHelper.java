package com.sj.basemodule.net;


import com.sj.basemodule.base.BaseActivity;

/**
 * Created by sj on 2017/12/4.
 * Description:登录和刷新token的请求
 */

public class RequestHelper {
    private BaseActivity activity;
    private RequestCallback callback;

    public RequestHelper(BaseActivity activity) {
        this.activity = activity;
    }

    public RequestHelper(BaseActivity activity, RequestCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public void login(final String account, String pwd) {
    }

    //  刷新token
    public void refreshToken() {
        STGApi.isRefreshToken = true;
    }

    public interface RequestCallback {
        /**
         * token更新
         */
        void onTokenUpdateSucceed();

        /**
         * 完成登陆
         */
        void onLoginSucceed();
    }
}
