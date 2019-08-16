package basemodule.sj.com.basic.base;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;


import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import basemodule.sj.com.basic.R;
import basemodule.sj.com.basic.common.NetWorkChangeEvent;
import basemodule.sj.com.basic.util.NetWorkUtil;

/**
 * content:规范Activity统一界面、逻辑处理 SwipeBackActivity为左滑可删除
 * author：sj
 * time: 2017/11/21 15:58
 * email：13658029734@163.com
 * phone:13658029734
 */

public abstract class BaseActivity extends RxAppCompatActivity {
    protected boolean mCheckNetWork = true; //默认检查网络状态
    WindowManager mWindowManager;
    WindowManager.LayoutParams mTipViewLayoutParams;
    WindowManager.LayoutParams mErrorViewLayoutParams;
    View mTipView;
    View mErrorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTipView();//初始化提示View
        initErrorView();//初始化错误信息的View
        EventBus.getDefault().register(this); //注册eventBus
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在无网络情况下打开APP时，系统不会发送网络状况变更的Intent，需要自己手动检查
        hasNetWork(NetWorkUtil.isConnected());
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消eventBus
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void finish() {
        super.finish();
        //当提示View被动态添加后直接关闭页面会导致该View内存溢出，所以需要在finish时移除
        if (mTipView != null && mTipView.getParent() != null) {
            mWindowManager.removeView(mTipView);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkChangeEvent(NetWorkChangeEvent event) {
        hasNetWork(event.isConnected);
    }


    protected abstract void reConnect();

    public void setCheckNetWork(boolean checkNetWork) {
        mCheckNetWork = checkNetWork;
    }

    private boolean isCheckNetWork() {
        return mCheckNetWork;
    }

    //初始化布局
    public abstract int initLayout();

    //初始化来源数据
    public abstract void initFromData();

    //初始化布局view
    public abstract void initLayoutView();

    //初始化本地数据
    public abstract void initLocalData();

    /**
     * EventBus 事件接收方法
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaseEvent event) {
    }

    /**
     * 无网的界面提示(广播触发自动)
     */
    private void initTipView() {
        LayoutInflater inflater = getLayoutInflater();
        mTipView = inflater.inflate(R.layout.layout_network_tip, null); //提示View布局
        mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mTipViewLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                /*  | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,*/
                PixelFormat.TRANSLUCENT);
        //使用非CENTER时，可以通过设置XY的值来改变View的位置
        mTipViewLayoutParams.gravity = Gravity.TOP;
        mTipViewLayoutParams.x = 0;
        mTipViewLayoutParams.y = 0;
    }

    private void hasNetWork(boolean has) {
        if (isCheckNetWork()) {
            //有网络
            if (has) {
                //说明前一步骤是无网络情况，mTipView 被加载到WindowManager上
                if (mTipView != null && mTipView.getParent() != null) {
                    mWindowManager.removeView(mTipView);
                    reConnect();
                }
            }
            //无网络
            else {
                if (mTipView.getParent() == null) {

                    if (mErrorView != null && mErrorView.getParent() != null) {
                        mWindowManager.removeView(mErrorView);
                    }

                    mTipView.findViewById(R.id.retry).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // mWindowManager.removeView(mTipView);
                            NetWorkUtil.openWirelessSettings();
                        }
                    });
                    mWindowManager.addView(mTipView, mTipViewLayoutParams);
                }
            }
        }
    }

    /**
     * 显示网络状态异常的界面（界面手动调用）
     */
    private void initErrorView() {
        LayoutInflater inflater = getLayoutInflater();
        mErrorView = inflater.inflate(R.layout.layout_error_tip, null); //提示View布局
        mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mErrorViewLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                /*     | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,*/
                PixelFormat.TRANSLUCENT);
        //使用非CENTER时，可以通过设置XY的值来改变View的位置
        mErrorViewLayoutParams.gravity = Gravity.CENTER;
        mErrorViewLayoutParams.x = 0;
        mErrorViewLayoutParams.y = 0;
    }

    /***
     * 显示网络状态异常的界面
     *  回调参数 可以自己自定义
     */
    public void showErrorView(String errorStr) {
        if (mErrorView != null && mErrorView.getParent() == null) {

            if (mTipView != null && mTipView.getParent() != null) {
                mWindowManager.removeView(mTipView);
            }

            TextView textView = mErrorView.findViewById(R.id.hint);
            textView.setText(errorStr);
            mErrorView.findViewById(R.id.retry).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mWindowManager.removeView(mErrorView);
                    reConnect();
                }
            });
            mWindowManager.addView(mErrorView, mErrorViewLayoutParams);
        }
    }

    /***
     * 隐藏异常状态界面
     */
    public void hideErrorView() {
        if (mErrorView != null) {
            mWindowManager.removeView(mErrorView);
        }
    }


}
