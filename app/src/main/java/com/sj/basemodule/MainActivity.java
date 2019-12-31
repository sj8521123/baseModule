package com.sj.basemodule;
import android.util.Log;
import com.hjq.toast.ToastUtils;
import basemodule.sj.com.basic.base.BaseActivity;
import basemodule.sj.com.basic.base.BaseEvent;

public class MainActivity extends BaseActivity {
    @Override
    protected void reConnect() {
        ToastUtils.show("reConnect");
    }

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initFromData() {
    }

    @Override
    public void initLayoutView() {
    }

    @Override
    public void initLocalData() {
    }
    @Override
    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);
    }
}
