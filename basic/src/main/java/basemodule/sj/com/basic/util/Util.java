package basemodule.sj.com.basic.util;

import android.content.Context;
import android.text.TextUtils;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : Utils初始化相关
 * </pre>
 */
public class Util {

    private static Context context;
    private static String currentUserPrefsName;

    private Util() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Util.context = context.getApplicationContext();
    }

    public static void setCurrentUserPrefsName(String currentUserPrefsName) {
        Util.currentUserPrefsName = currentUserPrefsName;
    }


    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null)
            return context;
        throw new NullPointerException("u should init first");
    }

    public static String getCurrentUserPrefsName() {
        if (!TextUtils.isEmpty(currentUserPrefsName))
            return currentUserPrefsName;
        throw new NullPointerException("u should init first");
    }


}