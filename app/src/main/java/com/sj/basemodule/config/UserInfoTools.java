package com.sj.basemodule.config;

import com.app.idea.net.token.RefreshTokenResponse;
import com.sj.basemodule.base.MyApplication;
import com.sj.basemodule.net.reponse.LoginResponse;

import basemodule.sj.com.basic.config.SPUtils;


/**
 * Created by zhpan on 2018/4/14.
 */
public class UserInfoTools {
    private static UserInfoBean sUserInfoBean;

    public static void init() {
        sUserInfoBean = getUserInfoBean();
        if (sUserInfoBean == null) {
            sUserInfoBean = new UserInfoBean();

            sUserInfoBean.setTokenId("");
        }
        setUserInfoBean(sUserInfoBean);
    }

    private static UserInfoBean getUserInfoBean() {
        if (sUserInfoBean == null) {
            sUserInfoBean = SPUtils.getInstance(MyApplication.currentUserPrefsName).getObject(UserInfoBean.class);
        }
        if (sUserInfoBean == null) {
            sUserInfoBean = new UserInfoBean();
        }
        return sUserInfoBean;
    }

    /**
     * Save user info to SharedPreferences
     */
    private static void setUserInfoBean(UserInfoBean userInfoBean) {
        sUserInfoBean = userInfoBean;
        SPUtils.getInstance(MyApplication.currentUserPrefsName).saveObject(sUserInfoBean);
    }

    /**
     * login success，save user information to SharedPreferences and post
     * an event to update UI
     */
    public static void login(LoginResponse response) {
        sUserInfoBean.setLogin(true);
        //sUserInfoBean.setUser(response.getUser());
        setUserInfoBean(sUserInfoBean);
    }

    /**
     * Logout and clear data，post an Event to update UI
     */
    public static void logout() {
        SPUtils.getInstance(MyApplication.currentUserPrefsName).remove(sUserInfoBean.getClass().getSimpleName());
        sUserInfoBean = null;
        init();
    }

    /**
     * update token
     *
     * @param response token response
     */
    public static void updateToken(RefreshTokenResponse response) {
        SPUtils.getInstance(MyApplication.currentUserPrefsName).put("token", response.getToken());
    }
}
