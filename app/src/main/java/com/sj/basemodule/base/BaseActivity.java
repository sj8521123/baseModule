package com.sj.basemodule.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * content:规范Activity统一界面、逻辑处理
 * author：sj
 * time: 2017/11/21 15:58
 * email：13658029734@163.com
 * phone:13658029734
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
    @Subscribe
    public void onEventMainThread(BaseEvent event) {
    }

}
