package com.sj.basemodule;


import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.hjq.toast.ToastUtils;
import com.sj.basemodule.adapter.OtherAdapter;

import java.util.ArrayList;
import java.util.List;

import basemodule.sj.com.basic.base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/app/OtherActivity")
public class OtherActivity extends BaseActivity {
    @BindView(R.id.recycle)
    RecyclerView recycle;
    private List<Integer> mDatas2 = new ArrayList<>();
    private OtherAdapter mAdater;
    @Override
    protected void reConnect() {
        ToastUtils.show("重新刷新");

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
        mDatas2.add(R.drawable.cat_1);
        mDatas2.add(R.drawable.cat_2);
        mDatas2.add(R.drawable.cat_3);
        mDatas2.add(R.drawable.cat_4);
        mDatas2.add(R.drawable.cat_5);
        mDatas2.add(R.drawable.cat_6);
        mDatas2.add(R.drawable.cat_7);
        mDatas2.add(R.drawable.cat_8);
        mDatas2.add(R.drawable.cat_9);
        mDatas2.add(R.drawable.bleach_9);
        mDatas2.add(R.drawable.cat_10);
        mDatas2.add(R.drawable.cat_11);
        mDatas2.add(R.drawable.cat_12);
        mDatas2.add(R.drawable.cat_13);
        mDatas2.add(R.drawable.cat_14);
        mDatas2.add(R.drawable.bleach_16);
        mDatas2.add(R.drawable.bleach_15);
        mDatas2.add(R.drawable.cat_15);
        mDatas2.add(R.drawable.bleach_14);
        mDatas2.add(R.drawable.cat_16);
        mDatas2.add(R.drawable.cat_17);
        mDatas2.add(R.drawable.bleach_15);
        mDatas2.add(R.drawable.cat_18);
        mDatas2.add(R.drawable.cat_19);
        mDatas2.add(R.drawable.bleach_14);
        mDatas2.add(R.drawable.cat_16);
        mDatas2.add(R.drawable.cat_17);
        mDatas2.add(R.drawable.bleach_15);
        mDatas2.add(R.drawable.cat_18);
        mDatas2.add(R.drawable.cat_19);
        mDatas2.add(R.drawable.bleach_14);
        mDatas2.add(R.drawable.cat_16);
        mDatas2.add(R.drawable.cat_17);
        mDatas2.add(R.drawable.bleach_15);
        mDatas2.add(R.drawable.cat_18);
        mDatas2.add(R.drawable.cat_19);
        mDatas2.add(R.drawable.bleach_14);
        mDatas2.add(R.drawable.cat_16);
        mDatas2.add(R.drawable.cat_17);
        mDatas2.add(R.drawable.bleach_15);
        mDatas2.add(R.drawable.cat_18);
        mDatas2.add(R.drawable.cat_19);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.COLUMN);
        layoutManager.setJustifyContent(JustifyContent.FLEX_END);
        recycle.setLayoutManager(layoutManager);
        mAdater = new OtherAdapter(R.layout.flexbox_item,mDatas2);
        recycle.setAdapter(mAdater);
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

