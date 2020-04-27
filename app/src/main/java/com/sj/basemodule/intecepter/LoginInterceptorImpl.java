package com.sj.basemodule.intecepter;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

import me.jessyan.autosize.utils.LogUtils;

/**
 * author: shijun
 * created on: 2020/3/16
 * description:
 */
@Interceptor(name = "login" ,priority = 6)
public class LoginInterceptorImpl implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String path = postcard.getPath();
        LogUtils.d(path);
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
        LogUtils.d("路由登录拦截器初始化成功"); //只会走一次
    }
}
