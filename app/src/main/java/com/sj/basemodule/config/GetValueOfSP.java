package com.sj.basemodule.config;


import com.sj.basemodule.base.MyApplication;

/**
 * 获取sp中常用的数据
 */
public class GetValueOfSP {
    public static String getToken() {
        return SPUtils.getInstance(MyApplication.currentUserPrefsName).getString(KeyAndValueUserPrefs.Key.ACCESS_TOKEN);
    }
}
