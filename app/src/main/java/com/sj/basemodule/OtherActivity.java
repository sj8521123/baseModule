package com.sj.basemodule;


import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import basemodule.sj.com.basic.base.BaseActivity;
import basemodule.sj.com.basic.base.ClassicsHead3;
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
                +"  flavor："+BuildConfig.VERSION_NAME
        );
//        refreshLayout.setRefreshHeader(new ClassicsHead3(this));
//        /*refreshLayout.setHeaderMaxDragRate(2);*/
//
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                //refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
//            }
//        });
//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(RefreshLayout refreshlayout) {
//                //refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
//            }
//        });
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
