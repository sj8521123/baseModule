package com.app.idea.net.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.app.idea.net.converter.GsonConverterFactory;
import com.app.idea.net.interceptor.HttpCacheInterceptor;
import com.app.idea.net.interceptor.HttpHeaderInterceptor;
import com.app.idea.net.interceptor.LoggingInterceptor;
import com.app.idea.utils.Util;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by zhpan on 2018/3/21.
 */

public class RetrofitUtils {
    public static OkHttpClient.Builder getOkHttpClientBuilder() {
        File cacheFile = new File(Util.getContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        return new OkHttpClient.Builder()
                .readTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                //自动设置cookie
                //.cookieJar(CookieUtil.getCookieJar())
                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(new HttpHeaderInterceptor())
                .addNetworkInterceptor(new HttpCacheInterceptor())
                // .sslSocketFactory(SslContextFactory.getSSLSocketFactoryForTwoWay())  // https认证 如果要使用https且为自定义证书 可以去掉这两行注释，并自行配制证书。
                // .hostnameVerifier(new SafeHostnameVerifier())
                .cache(cache);
    }

    public static Retrofit.Builder getRetrofitBuilder(String baseUrl) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        OkHttpClient okHttpClient = getOkHttpClientBuilder().build();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl);
    }
}
