package basemodule.sj.com.basic.weight;

import android.animation.ValueAnimator;
import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Scroller;

/**
 * 弹性滑动
 * Created by 13658 on 2018/7/20.
 */

public class CsTextView extends androidx.appcompat.widget.AppCompatTextView {
    private static final String TAG = "CsTextView";
    Scroller scroller ;
    float mLastX;
    float mLastY;
    public CsTextView(Context context) {
        super(context);
        scroller = new Scroller(context);
        smoothScrollTo2(-300,0);
    }

    public CsTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
        smoothScrollTo2(-300,0);
    }

    public CsTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
        smoothScrollTo2(-300,0);
    }
    private void smoothScrollTo2(int destX,int destY){
        //当前View内容的相对位置
        final int scrollX = getScrollX();
        //delaX偏移量
        final int delaX = destX - scrollX;
        ValueAnimator animator = ValueAnimator.ofInt(0,1).setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取每一帧占总数的百分比 0~1
                float fraction = animation.getAnimatedFraction();
                CsTextView.this.scrollTo(scrollX + (int)(delaX * fraction),0);
            }
        });
        animator.start();
    }
    private void smoothScrollTo(int destX,int destY){
        int scrollX = getScrollX();
        int deltaX = destX - scrollX;
        scroller.startScroll(scrollX,0,deltaX,0,10000);
        //发起重绘
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        //true 还没结束，false 结束
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            //不断重绘
            postInvalidate();
        }
    }
}
