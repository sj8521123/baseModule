package com.sj.basemodule.net;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sj.basemodule.base.MyApplication;
import com.sj.basemodule.net.interceptor.HttpCacheInterceptor;
import com.sj.basemodule.net.interceptor.HttpHeaderInterceptor;
import com.sj.basemodule.net.interceptor.LoggerInterceptor;
import com.sj.basemodule.util.CookieUtil;
import com.sj.basemodule.util.LogUtil;
import com.sj.basemodule.util.NetWorkUtil;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sj on 2017/12/4.
 * Description:对Retrofit进行初始化操作
 */
public class STGApi {
    public static volatile STGApi instance = null;
    public static boolean isRefreshToken;
    public static String BaseUrl = "";
    private STGApiService service;

    public STGApi() {
        File cacheFile = new File(MyApplication.mAppContext.getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(STGApiService.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(STGApiService.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .cache(cache)
                .cookieJar(CookieUtil.getCookieJar())
                .addInterceptor(new LoggerInterceptor("baseModule"))
                .addInterceptor(new HttpHeaderInterceptor())
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BaseUrl)
                .build();
        service = retrofit.create(STGApiService.class);
    }

    public static STGApiService getApiService() {
        return STGApi.getInstance().service;
    }

    //单例模式
    private static STGApi getInstance() {
        synchronized (STGApi.class) {
            if (instance == null) {
                instance = new STGApi();
            }
        }
        return instance;
    }
}
