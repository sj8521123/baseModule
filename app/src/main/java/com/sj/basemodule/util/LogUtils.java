/*
 * Copyright (C) 2016 venshine.cn@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sj.basemodule.util;

import android.util.Log;

import com.sj.basemodule.BuildConfig;


/**
 * Log日志打印操作
 *
 * @author Weiss
 */
public class LogUtils {
    //规定每段显示的长度
    private static int LOG_MAXLENGTH = 2000;

    public static void w(String logString) {
        if (BuildConfig.DEBUG) {
            Log.w(getClassName(), logString);
        }
    }

    /**
     * 获取当前类名
     *
     * @return
     */
    private static String getClassName() {
        // 这里的数组的index，即2，是根据你工具类的层级取的值，可根据需求改变
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        String result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1, result.length());
        return result;
    }

    /**
     * debug log
     *
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    /**
     * error log
     *
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg);
        }
    }

    /**
     * debug log
     *
     * @param msg
     */
    public static void d(String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(getClassName(), msg);
        }
    }

    /**
     * debug log
     *
     * @param msg
     */
    public static void i(String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(getClassName(), msg);
        }
    }

    /**
     * error log
     *
     * @param msg
     */
    public static void e(String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(getClassName(), msg);
        }
    }

    public static void i(String tag, String logString) {
        //log 每条最大4*1024个字符长度 超过则采用分段输出
        if (BuildConfig.DEBUG) {
            if (logString.length() > 4000) {
                for (int i = 0, count = 0; i < logString.length(); i += 4000, count++) {
                    if (i + 4000 < logString.length())
                        Log.i(tag + count, logString.substring(i, i + 4000));
                    else
                        Log.i(tag + count, logString.substring(i, logString.length()));
                }
            } else
                Log.i(tag, logString);
        }
    }

    public static void w(String tag, String logString) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, logString);
        }
    }

}
