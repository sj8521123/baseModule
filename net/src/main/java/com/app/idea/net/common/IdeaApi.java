package com.app.idea.net.common;

import retrofit2.Retrofit;

/**
 * 获取Retrofit的实例
 * cls 制定的service
 * baseUrl 基础的url
 */
public class IdeaApi {
    public static <T> T getApiService(Class<T> cls, String baseUrl) {
        Retrofit retrofit = RetrofitUtils.getRetrofitBuilder(baseUrl).build();
        return retrofit.create(cls);
    }
}
