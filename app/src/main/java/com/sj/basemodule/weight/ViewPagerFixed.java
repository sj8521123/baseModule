package com.sj.basemodule.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 解决在ViewPager中使用多点触控图片放缩时事件冲突导致崩溃BUG
 * Created by Luo on 2016/9/7.
 */
public class ViewPagerFixed extends android.support.v4.view.ViewPager{
    public ViewPagerFixed(Context context) {
        super(context);
    }

    public ViewPagerFixed(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
