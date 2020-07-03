package com.sj.basemodule;


import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import basemodule.sj.com.basic.base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherActivity extends BaseActivity {
    private static final String TAG = "OtherActivity";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected void reConnect() {

    }

    @Override
    public int initLayout() {
        return R.layout.fragment_huaxia_introduce;
    }

    @Override
    public void initFromData() {
    }

    @Override
    public void initLayoutView() {
        Log.i("====", "branch:master BuildType："+BuildConfig.BUILD_TYPE
                +"  flavor："+BuildConfig.FLAVOR
                +"  VERSION_NAME："+BuildConfig.VERSION_NAME
                +"  VERSION_CODE："+BuildConfig.VERSION_CODE

        );
    }

    @Override
    public void initLocalData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
