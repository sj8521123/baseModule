package basemodule.sj.com.basic.weight;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * content:自定义viewPager
 * author：sj
 * time: 2017/11/30 16:27
 * email：13658029734@163.com
 * phone:13658029734
 */

public class CustomViewPager extends ViewPager {
    //是否可以滑动
    private boolean isPagingEnabled = true;
    //是否显示滑动的过度效果
    private boolean isPagingAnimal = false;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //是否可以左右滑动
    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }

    //是否显示滑动的过度效果
    public void setPagingAnimal(boolean pagingAnimal) {
        isPagingAnimal = pagingAnimal;
    }

    //去除页面切换时的滑动翻页效果
    @Override
    public void setCurrentItem(int item) {
        // TODO Auto-generated method stub
        super.setCurrentItem(item, isPagingAnimal);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        try {
            return this.isPagingEnabled && super.onInterceptTouchEvent(event);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            return this.isPagingEnabled && super.onTouchEvent(event);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        //百度地图滑动冲突
        if (v.getClass().getName().equals("com.baidu.mapapi.map.MapView")) {
            return true;
        }
        //drawerLayout滑动冲突
        /*else if(v.getClass().getName().equals("android.support.v4.widget.DrawerLayout")){
            return true;
        }*/
        return super.canScroll(v, checkV, dx, x, y);
    }

}
