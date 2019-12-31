package com.app.idea.net.common;



import com.app.idea.net.token.IGlobalManager;
import com.app.idea.net.token.ProxyHandler;

import java.lang.reflect.Proxy;


/**
 * 创建代理后的实例对象 Proxy.newProxyInstance
 * 被代理对象 IdeaApiService
 * Created by sj on 2017/4/1.
 */

public class IdeaApiProxy {

    @SuppressWarnings("unchecked")
    public <T> T getApiService(Class<T> tClass, String baseUrl, IGlobalManager manager) {
        T t = RetrofitService.getRetrofitBuilder(baseUrl)
                .build().create(tClass);
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class<?>[] { tClass }, new ProxyHandler(t, manager));
    }
}
