package com.sj.basemodule.net;


import com.app.idea.net.common.Constants;
import com.app.idea.net.common.IdeaApi;

public class RetrofitHelper {
    private static IdeaApiService mIdeaApiService;

    public static IdeaApiService getApiService() {
        if (mIdeaApiService == null)
            mIdeaApiService = IdeaApi.getApiService(IdeaApiService.class, Constants.API_SERVER_URL);
        return mIdeaApiService;
    }
}
