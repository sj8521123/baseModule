package com.sj.basemodule.net.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * header添加
 */
public class HttpHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //  也可以统一配置用户名
        Request.Builder builder = chain.request().newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Content-Type-Charset", "utf-8")
                .header("Accept", "application/json");
      /*  if (isRefreshToken) {
            isRefreshToken = false;
                *//*String token = SPUtils.getInstance(MyApplication.currentUserPrefsName).getString(KeyAndValueUserPrefs.Key.ACCESS_TOKEN);
                builder.addHeader("token", token);*//*
        } else {
            builder.removeHeader("token");
        }*/
        return chain.proceed(builder.build());
    }
}
