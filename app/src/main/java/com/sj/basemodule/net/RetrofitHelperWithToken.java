package com.sj.basemodule.net;


import com.app.idea.net.common.Constants;
import com.app.idea.net.common.IdeaApiProxy;
import com.app.idea.net.token.IGlobalManager;
import com.app.idea.net.token.RefreshTokenResponse;
import com.sj.basemodule.config.UserInfoTools;

/**
 * Created by sj on 2018/3/22.
 */

public class RetrofitHelperWithToken {

    private static IdeaApiService mIdeaApiService;

    public static IdeaApiService getApiService() {
        if (mIdeaApiService == null)
            mIdeaApiService = new IdeaApiProxy().getApiService(IdeaApiService.class,
                    Constants.API_SERVER_URL, new IGlobalManager() {
                        @Override
                        public void logout() {
                            //重新登录
                            UserInfoTools.logout();
                        }

                        @Override
                        public void tokenRefresh(RefreshTokenResponse response) {
                            //本地保存token
                            UserInfoTools.updateToken(response);
                        }
                    });
        return mIdeaApiService;
    }
}
