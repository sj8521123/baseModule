package com.sj.basemodule.adapter;

import android.annotation.SuppressLint;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.AlignSelf;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.sj.basemodule.R;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import basemodule.sj.com.basic.util.GlideUtil;

public class OtherAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    public OtherAdapter(int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void convert(@NonNull BaseViewHolder helper, Integer item) {

        new GlideUtil().load(mContext, item, helper.getView(R.id.imageview), GlideUtil.NOT_TRANSFORM);
        ViewGroup.LayoutParams lp = helper.getView(R.id.imageview).getLayoutParams();
        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
            flexboxLp.setFlexGrow(1.0f);
            flexboxLp.setAlignSelf(AlignSelf.FLEX_END);
        }
    }
}
