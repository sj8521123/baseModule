package com.sj.basemodule.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sj.basemodule.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import basemodule.sj.com.basic.util.GlideUtil;

public class OtherAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    public OtherAdapter(int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Integer item) {
        new GlideUtil().load(mContext,item,helper.getView(R.id.image),GlideUtil.NOT_TRANSFORM);
    }
}
