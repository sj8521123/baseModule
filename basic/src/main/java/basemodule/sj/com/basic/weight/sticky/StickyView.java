package basemodule.sj.com.basic.weight.sticky;

import android.view.View;

/**
 * Created by sj on 2018/3/28.
 */

public interface StickyView {

    /**
     * 是否是吸附view
     *
     * @param view
     * @return
     */
    boolean isStickyView(View view);

    /**
     * 得到吸附view的itemType
     *
     * @return
     */
    int getStickViewType();
}
