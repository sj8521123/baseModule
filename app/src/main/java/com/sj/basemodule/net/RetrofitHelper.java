package com.sj.basemodule.net;


import com.app.idea.net.common.Constants;
import com.app.idea.net.common.IdeaApi;

/**
 * 1、服务内部异常
 * 创建Retrofit --- >  ProgressUtils中的doOnSubscribe准备 ---> 请求 --->GsonResponseBodyConverter 拦截Response--->
 * 抛出ServerResponseException、NoDataExceptionException、RemoteLoginExpiredException等服务内部的异常--->
 * DefaultObserver 中onError（）处理 --->onfinish() --->  ProgressUtils中 doOnTerminate结束
 * <p>
 * 2、http外部异常
 * 创建Retrofit --- >  ProgressUtils中的doOnSubscribe准备 ---> 请求 --->
 * DefaultObserver 中onError（）处理 --->onfinish() --->  ProgressUtils中 doOnTerminate结束
 * <p>
 * 3、正确
 * 创建Retrofit --- >  ProgressUtils中的doOnSubscribe准备 ---> 请求 --->GsonResponseBodyConverter 拦截Response---> 抛出内部对象
 * DefaultObserver onNext--->Activity onSuccess() --->onfinish()--->ProgressUtils中 doOnTerminate结束
 */
public class RetrofitHelper {
    private static IdeaApiService mIdeaApiService;

    public static IdeaApiService getApiService() {
        if (mIdeaApiService == null)
            mIdeaApiService = IdeaApi.getApiService(IdeaApiService.class, Constants.API_SERVER_URL);
        return mIdeaApiService;
    }
}
