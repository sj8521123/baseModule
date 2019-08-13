package com.sj.basemodule.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sj.basemodule.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OtherAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public OtherAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.name, item).addOnClickListener(R.id.name);
    }
}
