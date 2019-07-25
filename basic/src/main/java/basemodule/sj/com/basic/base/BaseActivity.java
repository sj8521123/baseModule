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

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import basemodule.sj.com.basic.R;
import basemodule.sj.com.basic.common.NetWorkChangeEvent;
import basemodule.sj.com.basic.util.NetWorkUtil;

/**
 * content:规范Activity统一界面、逻辑处理
 * author：sj
 * time: 2017/11/21 15:58
 * email：13658029734@163.com
 * phone:13658029734
 */

public abstract class BaseActivity extends RxAppCompatActivity {
    protected boolean mCheckNetWork = true; //默认检查网络状态
    WindowManager mWindowManager;
    WindowManager.LayoutParams mLayoutParams;
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

    private void hasNetWork(boolean has) {
        if (isCheckNetWork()) {
            //有网络
            if (has) {
                if (mTipView != null && mTipView.getParent() != null) {
                    mWindowManager.removeView(mTipView);
                    reConnect();
                }
            }
            //无网络
            else {
                if (mTipView.getParent() == null) {
                    mWindowManager.addView(mTipView, mLayoutParams);
                }
            }
        }
    }

    protected abstract void reConnect();

    public void setCheckNetWork(boolean checkNetWork) {
        mCheckNetWork = checkNetWork;
    }

    public boolean isCheckNetWork() {
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
        mLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        //使用非CENTER时，可以通过设置XY的值来改变View的位置
        mLayoutParams.gravity = Gravity.TOP;
        mLayoutParams.x = 0;
        mLayoutParams.y = 0;
    }

    /**
     * 显示网络状态异常的界面（界面手动调用）
     */
    private void initErrorView() {
        LayoutInflater inflater = getLayoutInflater();
        mErrorView = inflater.inflate(R.layout.layout_error_tip, null); //提示View布局
        mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        //使用非CENTER时，可以通过设置XY的值来改变View的位置
        mLayoutParams.gravity = Gravity.CENTER;
        mLayoutParams.x = 0;
        mLayoutParams.y = 0;
    }

    /***
     * 显示网络状态异常的界面
     *  回调参数 可以自己自定义
     *       1 服务器繁忙 2 网络错误 3  打开定位服务错误
     * @param flag
     */
    public void showErrorView(int flag) {
        TextView textView = mErrorView.findViewById(R.id.hint);
        switch (flag) {
            case 1:
                textView.setText("服务器繁忙");
                break;
            case 2:
                textView.setText("网络错误");
                break;
            case 3:
                textView.setText("定位服务错误");
                break;
        }
        mWindowManager.addView(mErrorView, mLayoutParams);
    }

    /***
     * 隐藏异常状态界面
     */
    public void hideErrorView() {
        mWindowManager.removeView(mErrorView);
    }


}
