package com.sj.basemodule;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;

import java.util.List;

/**
 * Created by 13658 on 2018/8/7.
 */

public  class TestAdapter extends MultipleItemRvAdapter {

    public TestAdapter(@Nullable List data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(Object o) {
        return 0;
    }

    @Override
    public void registerItemProvider() {

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
}
