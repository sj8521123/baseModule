package com.sj.basemodule;


import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.sj.basemodule.adapter.TestAdapter;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import basemodule.sj.com.basic.base.BaseActivity;
import basemodule.sj.com.basic.util.ToastUtil;
import basemodule.sj.com.basic.weight.decoration.GridItemDecoration;
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        // mRecycleView.addItemDecoration(new GridSectionAverageGapItemDecoration(5,5,5,5));
        /* LinearLayoutManager manager = new LinearLayoutManager(this);*/
        mRecycleView.setLayoutManager(gridLayoutManager);
        //mRecycleView.addItemDecoration(new GridSectionAverageGapItemDecoration(5,5,5,5));
        mRecycleView.addItemDecoration(new GridItemDecoration(this, R.drawable.list_divider_vertical, LinearLayoutManager.VERTICAL));
        mRecycleView.addItemDecoration(new GridItemDecoration(this, R.drawable.list_divider_horizontal, LinearLayoutManager.HORIZONTAL));
        mAdapter = new TestAdapter(R.layout.test_item, mDatas);
        mRecycleView.setAdapter(mAdapter);


        LinearSmoothScroller mScroller = new TopSmoothScroller(this);
        for (int i = 0; i < 50; i++) {
            mAdapter.getData().add(new String("helloWorld" + i));
        }
        mAdapter.notifyDataSetChanged();
        mScroller.setTargetPosition(10);
        mRecycleView.getLayoutManager().startSmoothScroll(mScroller);
        /*mAdapter.onAttachedToRecyclerView(mRecycleView);*/
       /* io.reactivex.Observable.timer(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        for (int i = 0; i < 50; i++) {
                            mAdapter.getData().add(new String("helloWorld" + i));
                        }
                        mAdapter.notifyDataSetChanged();
                        // manager.scrollToPositionWithOffset(10, 0);
                        mScroller.setTargetPosition(10);
                        mRecycleView.getLayoutManager().startSmoothScroll(mScroller);
                        *//*mRecycleView.smoothScrollToPosition(10);*//*

                    }
                });*/
        mRecycleView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                mScroller.setTargetPosition(10);
                mRecycleView.getLayoutManager().startSmoothScroll(mScroller);
                /*mRecycleView.smoothScrollToPosition(10);*/
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

