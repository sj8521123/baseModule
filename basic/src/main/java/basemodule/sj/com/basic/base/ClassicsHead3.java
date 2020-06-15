package basemodule.sj.com.basic.base;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import basemodule.sj.com.basic.R;

/**
 * @author shijun
 * <p>
 * Created on 2020/4/23.
 */
public class ClassicsHead3 extends LinearLayout implements RefreshHeader {
    private static final String TAG = "ClassicsHead3";
    private TextView mHintText;
    private LottieAnimationView mLottieAnimationView;
    private float startAnimalPercent = 0.5f;
    private int lastFrame;

    public ClassicsHead3(Context context) {
        super(context);
        initView(context);
    }

    public ClassicsHead3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ClassicsHead3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        //内部元素居中
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        View view = View.inflate(context, R.layout.refresh_head, this);
        mLottieAnimationView = view.findViewById(R.id.LottieAnimationView);
        mLottieAnimationView.setAnimation("refreshjojo_complete.json");
        mHintText = view.findViewById(R.id.hintText);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        int frame = (int) (((1 / startAnimalPercent) * (percent - startAnimalPercent)) * 33);
        if (percent >= startAnimalPercent && percent < 1.0 && frame != lastFrame) {
            mLottieAnimationView.setFrame(frame);
            lastFrame = frame;
        }
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        mLottieAnimationView.cancelAnimation();
        mHintText.setText("刷新完成");
        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            //down
            case None:
                mLottieAnimationView.cancelAnimation();
                mLottieAnimationView.setMinAndMaxFrame(0, 33);
                mLottieAnimationView.setFrame(0);
                break;
            //move的距离<触发刷新距离
            case PullDownToRefresh:
                mLottieAnimationView.cancelAnimation();
                mLottieAnimationView.setMinAndMaxFrame(0, 33);
                mHintText.setText("下拉开始刷新");
                break;
            //move的距离>=触发刷新距离
            case ReleaseToRefresh:
                mHintText.setText("释放立即刷新");
                mLottieAnimationView.setMinAndMaxFrame(13, 34);
                mLottieAnimationView.setRepeatCount(ValueAnimator.INFINITE);
                mLottieAnimationView.playAnimation();
                break;
            //触发了刷新 up
            case Refreshing:
                mHintText.setText("正在刷新");
                mLottieAnimationView.setMinAndMaxFrame(35, 56);
                mLottieAnimationView.setRepeatCount(ValueAnimator.INFINITE);
                mLottieAnimationView.playAnimation();
                break;

        }
    }
}
