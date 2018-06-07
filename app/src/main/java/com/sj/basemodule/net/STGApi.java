package com.sj.basemodule.net;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sj.basemodule.base.MyApplication;
import com.sj.basemodule.util.CookieUtil;
import com.sj.basemodule.util.LogUtil;
import com.sj.basemodule.util.network.NetWorkUtil;

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
                .addInterceptor(new LoggingInterceptor())
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

    //日志拦截器
    private class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();
            long t1 = System.nanoTime();//请求发起的时间
            LogUtil.i("okhttp", String.format("发送请求 %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();//收到响应的时间
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            LogUtil.i("okhttp", String.format(Locale.ENGLISH, "接收响应: [%s] %n返回json:【%s】 %.1fms%n%s",
                    response.request().url(),
                    responseBody.string(),
                    (t2 - t1) / 1e6d,
                    response.headers()));

            return response;
        }
    }

    //  添加请求头的拦截器
    private class HttpHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //  也可以统一配置用户名
            Request.Builder builder = chain.request().newBuilder()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Content-Type-Charset", "utf-8")
                    .header("Accept", "application/json");
            if (isRefreshToken) {
                isRefreshToken = false;
                /*String token = SPUtils.getInstance(MyApplication.currentUserPrefsName).getString(KeyAndValueUserPrefs.Key.ACCESS_TOKEN);
                builder.addHeader("token", token);*/
            } else {
                builder.removeHeader("token");
            }
            return chain.proceed(builder.build());
        }
    }

    //  配置缓存的拦截器
    private class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtil.isConnected()) {  //没网强制从缓存读取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                LogUtil.d("Okhttp", "no network");
            }
            Response originalResponse = chain.proceed(request);
            if (NetWorkUtil.isConnected()) {
                // TODO: 2017/12/12  有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                //写入到配置中去
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }

}
