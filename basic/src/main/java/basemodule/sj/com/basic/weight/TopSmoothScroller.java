package basemodule.sj.com.basic.weight;

import android.content.Context;

import androidx.recyclerview.widget.LinearSmoothScroller;

/**
 * 针对layoutManager 滑动的处理
 */
public class TopSmoothScroller extends LinearSmoothScroller {
    public TopSmoothScroller(Context context) {
        super(context);
    }

    @Override
    protected int getHorizontalSnapPreference() {
        return SNAP_TO_START;//具体见源码注释
    }

    @Override
    protected int getVerticalSnapPreference() {
        return SNAP_TO_START;//具体见源码注释
    }
}