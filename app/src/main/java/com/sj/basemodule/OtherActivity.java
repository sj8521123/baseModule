package com.sj.basemodule;


import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.sj.basemodule.adapter.TestAdapter;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import basemodule.sj.com.basic.base.BaseActivity;
import basemodule.sj.com.basic.util.ScreenUtil;
import basemodule.sj.com.basic.util.ToastUtil;
import basemodule.sj.com.basic.weight.TopSmoothScroller;
import basemodule.sj.com.basic.weight.decoration.GridDividerItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherActivity extends BaseActivity {
    private static final String TAG = "OtherActivity";
    @BindView(R.id.mRecycleView)
    RecyclerView mRecycleView;
    private TestAdapter mAdapter;
    private ArrayList<String> mDatas;

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
        mDatas = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecycleView.setLayoutManager(gridLayoutManager);
        mRecycleView.addItemDecoration(new GridDividerItemDecoration(this, ScreenUtil.dp2px(2), ScreenUtil.dp2px(2), ScreenUtil.dp2px(2), true, true, Color.RED));
        mAdapter = new TestAdapter(R.layout.test_item, mDatas);
        mRecycleView.setAdapter(mAdapter);


        LinearSmoothScroller mScroller = new TopSmoothScroller(this);
        for (int i = 0; i < 50; i++) {
            mAdapter.getData().add(new String("h" + i));
        }
        mAdapter.notifyDataSetChanged();

        mScroller.setTargetPosition(10);
        mRecycleView.getLayoutManager().startSmoothScroll(mScroller);

        mRecycleView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                mScroller.setTargetPosition(10);
                mRecycleView.getLayoutManager().startSmoothScroll(mScroller);

            }
        });


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

