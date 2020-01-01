
package com.app.idea.net.token;

import android.text.TextUtils;

import com.app.idea.net.common.CommonService;
import com.app.idea.net.common.Constants;
import com.app.idea.net.common.DefaultObserver;
import com.app.idea.net.common.RetrofitService;
import com.app.idea.net.exception.TokenExpiredException;
import com.app.idea.net.exception.RefreshTokenExpiredException;
import com.app.idea.net.module.BaseRequest;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import basemodule.sj.com.basic.config.SPUtils;
import basemodule.sj.com.basic.util.Util;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public class ProxyHandler implements InvocationHandler {

    private final static String TOKEN = "token";

    private final static int REFRESH_TOKEN_VALID_TIME = 30;
    private static long tokenChangedTime = 0;
    private Throwable mRefreshTokenError = null;
    private boolean mIsTokenNeedRefresh;

    private Object mProxyObject;
    private IGlobalManager mGlobalManager;

    public ProxyHandler(Object proxyObject, IGlobalManager globalManager) {
        mProxyObject = proxyObject;
        mGlobalManager = globalManager;
    }

    /**
     * Refresh the token when the current token is invalid.
     *
     * @return Observable
     */
    private Observable<?> refreshTokenWhenTokenInvalid() {
        synchronized (ProxyHandler.class) {
            //服务端发送token过期 但本地检测token还在有效时长内，直接本地缓存中获取
            if (new Date().getTime() - tokenChangedTime < REFRESH_TOKEN_VALID_TIME) {
                mIsTokenNeedRefresh = true;
                //触发重订阅
                return Observable.just(true);
            }
            //服务端发送token过期 本地检测token未在有效时长内，重新获取token
            else {
                //CountDownLatch 控制执行顺序
                final CountDownLatch latch= new CountDownLatch(1);
                RetrofitService
                        .getRetrofitBuilder(Constants.API_SERVER_URL)
                        .build()
                        .create(CommonService.class)
                        .refreshToken()
                        .subscribe(new DefaultObserver<RefreshTokenResponse>() {
                            @Override
                            public void onSuccess(RefreshTokenResponse response) {
                                if (response != null) {
                                    //讲新token写入到本地
                                    mGlobalManager.tokenRefresh(response);
                                    //请求方法参数中更新token
                                    mIsTokenNeedRefresh = true;
                                    //本地记录token刷新时间
                                    tokenChangedTime = new Date().getTime();
                                }
                                latch.countDown();
                            }
                            @Override
                            public void onError(Throwable e) {
                                //super.onError(e);
                                mRefreshTokenError = e;
                                latch.countDown();
                            }
                        });
                try {
                    //阻塞
                    latch.await();
                    if (mRefreshTokenError != null) {
                        Observable<Object> error = Observable.error(mRefreshTokenError);
                        mRefreshTokenError = null;
                        //获取服务端token异常 不重订阅 并抛出onError()错误
                        return error;
                    }
                    else {
                        //触发重订阅
                        return Observable.just(true);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //不重订阅 并抛出onError()错误
                    return Observable.error(e);
                }

            }
        }
    }

    /**
     * Update the token of the args in the method.
     * 适合以表单格式提交,替换表单中为“token”的旧值
     * 如果是post请求且提交格式为json可以自行添加,不适用于token放在请求头的方式。
     */
    @SuppressWarnings("unchecked")
    private void updateMethodToken(Method method, Object[] args) {
        String token = (String) SPUtils.getInstance(Util.getCurrentUserPrefsName()).get("token", "");
        if (mIsTokenNeedRefresh && !TextUtils.isEmpty(token)) {
            Annotation[][] annotationsArray = method.getParameterAnnotations();
            Annotation[] annotations;
            if (annotationsArray.length > 0) {
                for (int i = 0; i < annotationsArray.length; i++) {
                    annotations = annotationsArray[i];
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof FieldMap || annotation instanceof QueryMap) {
                            if (args[i] instanceof Map)
                                ((Map<String, Object>) args[i]).put(TOKEN, token);
                        } else if (annotation instanceof Query) {
                            //匹配到token
                            if (TOKEN.equals(((Query) annotation).value()))
                                args[i] = token;
                        } else if (annotation instanceof Field) {
                            //匹配到token
                            if (TOKEN.equals(((Field) annotation).value()))
                                args[i] = token;
                        } else if (annotation instanceof Part) {
                            //匹配到token
                            if (TOKEN.equals(((Part) annotation).value())) {
                                RequestBody tokenBody = RequestBody.create(MediaType.parse("multipart/form-data"), token);
                                args[i] = tokenBody;
                            }
                        }
                        //body
                        else if (annotation instanceof Body) {
                            if (args[i] instanceof BaseRequest) {
                                BaseRequest requestData = (BaseRequest) args[i];
                                requestData.setToken(token);
                                args[i] = requestData;
                            }
                        }
                    }
                }
            }
            mIsTokenNeedRefresh = false;
        }
    }

    @Override
    public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
        return Observable.just(true)
                .flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Object o) throws Exception {
                        //进行aop编程
                        try {
                            try {
                                if (mIsTokenNeedRefresh) {
                                    //更新token操作
                                    updateMethodToken(method, args);
                                }
                                //继续请求的执行  mProxyObject被代理对象,不能使用proxy 代理对象实例， 否则会一直触发invoke调用
                                return (Observable<?>) method.invoke(mProxyObject, args);
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }).retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> observable) throws Exception {

                        return observable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                                // token过期
                                if (throwable instanceof TokenExpiredException) {
                                    return refreshTokenWhenTokenInvalid();
                                }
                                // RefreshToken过期，执行退出登录的操作。
                                else if (throwable instanceof RefreshTokenExpiredException) {
                                    // Token 不存在，执行退出登录的操作。（为了防止多个请求，都出现 Token 不存在的问题，
                                    // 这里需要取消当前所有的网络请求）
                                    mGlobalManager.logout();
                                    //不重订阅，并抛出onError()错误
                                    return Observable.error(throwable);
                                }
                                //不重订阅，并抛出onError()错误
                                return Observable.error(throwable);
                            }
                        });
                    }
                });
    }
}
