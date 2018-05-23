package com.sj.basemodule.util;


import com.sj.basemodule.base.MyApplication;
import com.sj.basemodule.config.KeyAndValueUserPrefs;
import com.sj.basemodule.config.SPUtils;
import com.sj.basemodule.util.persistentcookiejar.ClearableCookieJar;
import com.sj.basemodule.util.persistentcookiejar.PersistentCookieJar;
import com.sj.basemodule.util.persistentcookiejar.cache.SetCookieCache;
import com.sj.basemodule.util.persistentcookiejar.persistence.CookiePersistor;
import com.sj.basemodule.util.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.List;

import okhttp3.Cookie;

/**
 * content:cookie维护与管理类
 * author：sj
 * time: 2017/12/25 13:58
 * email：13658029734@163.com
 * phone:13658029734
 */

public class CookieUtil {
    private static PersistentCookieJar persistentCookieJar;

    //获取到Okhttp CookieJar 保存于请求的管理
    public static ClearableCookieJar getCookieJar() {
        persistentCookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApplication.mAppContext));
        return persistentCookieJar;
    }

    //是否本地缓存过该Cookie
    public static boolean isExitCookie() {
        CookiePersistor cookiePersistor = new SharedPrefsCookiePersistor(MyApplication.mAppContext);
        List<Cookie> cookies = cookiePersistor.loadAll();
        if (cookies != null && cookies.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //清空本地Cookie 用于退出登录
    public static void cleanCookieAndToken() {
        //清空cookie 以及本地cache
        persistentCookieJar.clear();
        //清空token
        SPUtils.getInstance(MyApplication.currentUserPrefsName).put(KeyAndValueUserPrefs.Key.ACCESS_TOKEN, "");
    }
}
