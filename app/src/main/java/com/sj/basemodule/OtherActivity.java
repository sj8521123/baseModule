package com.sj.basemodule;


import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sj.basemodule.adapter.OtherAdapter;
import com.sj.basemodule.adapter.TestAdapter;
import com.sj.basemodule.adapter.TestAdapter2;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import basemodule.sj.com.basic.base.BaseActivity;
import basemodule.sj.com.basic.util.ToastUtil;
import basemodule.sj.com.basic.weight.TopSmoothScroller;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherActivity extends BaseActivity {

    @BindView(R.id.mRecycleView1)
    RecyclerView mRecycleView1;
    @BindView(R.id.mRecycleView2)
    RecyclerView mRecycleView2;
    @BindView(R.id.mRecycleView3)
    RecyclerView mRecycleView3;
    @BindView(R.id.mRecycleView4)
    RecyclerView mRecycleView4;
    private List<String> mDatas1;
    private List<String> mDatas2;
    private List<String> mDatas3;
    private List<String> mDatas4;
    private TestAdapter2 testAdapter1;
    private TestAdapter2 testAdapter2;
    private TestAdapter2 testAdapter3;
    private TestAdapter2 testAdapter4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void reConnect() {
        ToastUtil.show("重新刷新");

    }

    @Override
    public int initLayout() {
        return R.layout.fragment_loansign_layout;
    }

    @Override
    public void initFromData() {
    }


    private void initRefresh() {
    }

    @Override
    public void initLayoutView() {
        mDatas1 = new ArrayList<>();
        mDatas2 = new ArrayList<>();
        mDatas3 = new ArrayList<>();
        mDatas4 = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            mDatas1.add(i + "");
        }
        for (int i = 0; i < 2; i++) {
            mDatas2.add(i + "");
        }
        for (int i = 0; i < 3; i++) {
            mDatas3.add(i + "");
        }
        for (int i = 0; i < 4; i++) {
            mDatas4.add(i + "");
        }
        mRecycleView1.setLayoutManager(new GridLayoutManager(this, 4));
        mRecycleView2.setLayoutManager(new GridLayoutManager(this, 4));
        mRecycleView3.setLayoutManager(new GridLayoutManager(this, 4));
        mRecycleView4.setLayoutManager(new GridLayoutManager(this, 4));
        testAdapter1 = new TestAdapter2(R.layout.adapter_item_content, mDatas1);
        testAdapter2 = new TestAdapter2(R.layout.adapter_item_content, mDatas2);
        testAdapter3 = new TestAdapter2(R.layout.adapter_item_content, mDatas3);
        testAdapter4 = new TestAdapter2(R.layout.adapter_item_content, mDatas4);
        mRecycleView1.setAdapter(testAdapter1);
        mRecycleView2.setAdapter(testAdapter2);
        mRecycleView3.setAdapter(testAdapter3);
        mRecycleView4.setAdapter(testAdapter4);
    }

    @Override
    public void initLocalData() {

    }

}

