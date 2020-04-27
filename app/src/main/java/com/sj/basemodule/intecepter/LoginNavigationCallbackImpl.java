package com.sj.basemodule.intecepter;

import android.graphics.LightingColorFilter;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;

/**
 * author: shijun
 * created on: 2020/3/16
 * description:
 */
public class LoginNavigationCallbackImpl  implements NavigationCallback {
    @Override //找到了
    public void onFound(Postcard postcard) {

    }

    @Override //找不到了
    public void onLost(Postcard postcard) {

    }

    @Override    //跳转成功了
    public void onArrival(Postcard postcard) {
        Log.i("xxxxxx", "onArrival: " );
    }

    @Override
    public void onInterrupt(Postcard postcard) {
        String path = postcard.getPath();
        Log.i("xxxxxx", "onInterrupt: " +path);
         /*   Bundle bundle = postcard.getExtras();
            // 被登录拦截了下来了
            // 需要调转到登录页面，把参数跟被登录拦截下来的路径传递给登录页面，登录成功后再进行跳转被拦截的页面
            ARouter.getInstance().build(ConfigConstants.LOGIN_PATH)
                    .with(bundle)
                    .withString(ConfigConstants.PATH, path)
                    .navigation();*/
    }
}