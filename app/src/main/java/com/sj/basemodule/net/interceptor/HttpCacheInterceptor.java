package com.sj.basemodule.net.interceptor;

import com.sj.basemodule.util.LogUtil;
import com.sj.basemodule.util.NetWorkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * http缓存
 */
public class HttpCacheInterceptor implements Interceptor {

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