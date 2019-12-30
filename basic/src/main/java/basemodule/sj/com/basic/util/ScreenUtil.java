package basemodule.sj.com.basic.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import basemodule.sj.com.basic.base.ContextProvider;
import me.jessyan.autosize.utils.AutoSizeUtils;
import me.jessyan.autosize.utils.ScreenUtils;


/**
 * 屏幕工具
 */
public class ScreenUtil {
    //dp转px
    public static int dp2px(float dp) {
        return AutoSizeUtils.dp2px(ContextProvider.get().getContext(), dp);
    }

    //sp转px
    public static int sp2px(float sp) {
        return AutoSizeUtils.sp2px(ContextProvider.get().getContext(), sp);
    }

    //获取屏幕宽度
    public static int screenWidth() {
        int[] screenSize = ScreenUtils.getScreenSize(ContextProvider.get().getContext());
        return screenSize[0];//宽度
    }

    //获取屏幕高度
    public static int screenHeight() {
        int[] screenSize = ScreenUtils.getScreenSize(ContextProvider.get().getContext());
        return screenSize[1];//宽度
    }


}
