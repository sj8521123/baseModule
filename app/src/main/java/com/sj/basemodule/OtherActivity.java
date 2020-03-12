package com.sj.basemodule;


import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.util.SparseArray;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.toast.ToastUtils;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.HashMap;
import java.util.Map;

import basemodule.sj.com.basic.base.BaseActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path="/app/OtherActivity")
public class OtherActivity extends BaseActivity {
    private static final String TAG = "OtherActivity";
    @Autowired
    Long key1;
    @Autowired
    String key2;
    @Autowired
    Student key3;
    @Override
    protected void reConnect() {
        ToastUtils.show("重新刷新");

    }

    @Override
    public int initLayout() {
        ARouter.getInstance().inject(this);
        return R.layout.fragment_huaxia_introduce;
    }

    @Override
    public void initFromData() {

    }
    @Override
    public void initLayoutView() {
    }

    @Override
    public void initLocalData() {

        LiveEventBus.get("hello", String.class).observe(this, new Observer<String>() {

            @Override
            public void onChanged(String s) {

                ToastUtils.show(s + "未采用sti");
            }
        });
    }

    @OnClick(R.id.know)
    public void onViewClicked() {
        Log.i(TAG, "a:" + key1+"   key2："+key2 +"student :"+key3.getName());
        LiveEventBus.get("haha").post(true);


    }
}

