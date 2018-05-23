package com.sj.basemodule.util;


import com.sj.basemodule.base.MyApplication;

import es.dmoral.toasty.Toasty;

/**
 * ToastUtil
 */

public class ToastUtil {

    public static void success(String message) {
        Toasty.success(MyApplication.mAppContext, message, 4, true).show();
    }

    public static void fail(String message) {
        Toasty.error(MyApplication.mAppContext, message, 6).show();
    }

    public static void info(String message) {
        Toasty.info(MyApplication.mAppContext, message, 4).show();
    }
}
