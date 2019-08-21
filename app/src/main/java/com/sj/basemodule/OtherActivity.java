package com.sj.basemodule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.billy.android.swipe.SmartSwipeRefresh;
import com.billy.android.swipe.consumer.SlidingConsumer;
import com.billy.android.swipe.consumer.StretchConsumer;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sj.basemodule.adapter.OtherAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.recyclerview.widget.RecyclerView;
import basemodule.sj.com.basic.base.BaseActivity;
import basemodule.sj.com.basic.util.ToastUtil;
import basemodule.sj.com.basic.weight.ScrawlView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OtherActivity extends BaseActivity {
    private static final String TAG = "OtherActivity";
    @BindView(R.id.mRecycle)
    RecyclerView mRecycle;
    OtherAdapter mAdapter;
    List<String> datas;

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
        datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("name" + (i + 1));
        }
        mAdapter = new OtherAdapter(R.layout.other_item, datas);
        mAdapter.bindToRecyclerView(mRecycle);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.show(datas.get(position));
                startActivity(new Intent(OtherActivity.this, NetActivity.class));
            }
        });


        //enable recyclerView top & bottom swipe refersh
        SmartSwipeRefresh.behindMode(findViewById(R.id.refresh), false)
                .setDataLoader(loader)
                //get SwipeConsumer
                .getSwipeConsumer()
                .as(SlidingConsumer.class)
                .setEdgeAffinity(true)
                //disable nested touch scroll
                .setDisableNestedScroll(true)
                //disable nested non-touch scroll
                .setDisableNestedFly(true)
                //add StretchConsumer as the secondary SwipeConsumer to handle the nested scroll event
                .addConsumer(new StretchConsumer())
                //enable StretchConsumer top & bottom directions
                .enableVertical();


        /*SmartSwipeRefresh.drawerMode(findViewById(R.id.mRecycle), false).setDataLoader(loader);*/
    }


    @Override
    public void initLayoutView() {

    }


    @Override
    public void initLocalData() {

    }
}
