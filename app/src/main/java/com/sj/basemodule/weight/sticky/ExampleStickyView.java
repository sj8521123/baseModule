package com.sj.basemodule.weight.sticky;

import android.view.View;

/**
 * Created by sj on 2018/3/28.
 */

public class ExampleStickyView implements StickyView {

    @Override
    public boolean isStickyView(View view) {
        return (Boolean) view.getTag();
    }

    @Override
    public int getStickViewType() {
        return 1;
    }
}
